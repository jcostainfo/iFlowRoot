package pt.iflow.blocks;

import java.util.Hashtable;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import pt.iflow.api.blocks.Block;
import pt.iflow.api.blocks.Port;
import pt.iflow.api.notification.Email;
import pt.iflow.api.notification.EmailManager;
import pt.iflow.api.notification.EmailTemplate;
import pt.iflow.api.processdata.ProcessData;
import pt.iflow.api.utils.Const;
import pt.iflow.api.utils.Logger;
import pt.iflow.api.utils.UserInfoInterface;
import pt.iflow.api.utils.Utils;

/**
 * <p>Title: BlockEmail</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: iKnow</p>
 * @author Jose Costa
 */

public class BlockEmail extends Block {
  public Port portIn, portSuccess, portError;


  // check constants with editor altera atributos classes
  private static final String _sSELECT = "Escolha";
  public final static String sEMAIL_FROM = "from";
  public final static String sEMAIL_TO = "to";
  public final static String sEMAIL_SUBJECT = "subject";
  public final static String sEMAIL_MESSAGE = "message";
  public final static String sEMAIL_TEMPLATE = "template";

  public BlockEmail(int anFlowId,int id, int subflowblockid, String filename) {
    super(anFlowId,id, subflowblockid, filename);
    hasInteraction = false;
  }

  public Port[] getOutPorts (UserInfoInterface userInfo) {
    Port[] retObj = new Port[2];
    retObj[0] = portSuccess;
    retObj[1] = portError;
    return retObj;
  }

  public Port getEventPort() {
    return null;
  }

  public Port[] getInPorts (UserInfoInterface userInfo) {
    Port[] retObj = new Port[1];
    retObj[0] = portIn;
    return retObj;
  }

  /**
   * No action in this block
   *
   * @param dataSet a value of type 'DataSet'
   * @return always 'true'
   */
  public String before(UserInfoInterface userInfo, ProcessData procData) {
    return "";
  }

  /**
   * No action in this block
   *
   * @param dataSet a value of type 'DataSet'
   * @return always 'true'
   */
  public boolean canProceed(UserInfoInterface userInfo, ProcessData procData) {
    return true;
  }

  /**
   * Executes the block main action
   *
   * @param dataSet a value of type 'DataSet'
   * @return the port to go to the next block
   */
  public Port after(UserInfoInterface userInfo, ProcessData procData) {
    Port outPort = portSuccess;
    StringBuffer logMsg = new StringBuffer();
    
    String user = userInfo.getUtilizador();
    
    if(!Const.bUSE_EMAIL) {
      outPort = portError;
      Logger.warning(user, this, "after", 
          procData.getSignature() + 
          "email use is disabled by configuration... going to exit through port error.");
    } else {
      try {
        String aTo = getAttribute(sEMAIL_TO);
        String aFrom = getAttribute(sEMAIL_FROM);
        String aSubject = getAttribute(sEMAIL_SUBJECT);
        String aMessage = getAttribute(sEMAIL_MESSAGE);
        String template = getAttribute(sEMAIL_TEMPLATE);
        if (Logger.isDebugEnabled()) {
          Logger.debug(user, this, "after", "ATTR To: "+aTo);
          Logger.debug(user, this, "after", "ATTR From: "+aFrom);
          Logger.debug(user, this, "after", "ATTR Subject: "+aSubject);
          Logger.debug(user, this, "after", "ATTR Message: "+aMessage);
          Logger.debug(user, this, "after", "ATTR Template: "+template);
        }
        
        List<String> tos = null;
        String to = null;
        try {
          to = procData.transform(userInfo, aTo);
          tos = Utils.tokenize(to, EmailManager.sSEPARATOR);
        } catch (Exception e) {
          Logger.warning(user, this, "after", 
              procData.getSignature() + "exception transforming to: " + aTo);
        }
        String from = aFrom;
        try {
          from = procData.transform(userInfo, aFrom);
        } catch (Exception e) {
          Logger.warning(user, this, "after", 
              procData.getSignature() + "exception transforming from: " + aFrom);
        }
        String subject = aSubject;
        try {
          subject = procData.transform(userInfo, aSubject);
        } catch (Exception e) {
          Logger.warning(user, this, "after", 
              procData.getSignature() + "exception transforming subject: " + aSubject);
        }
        String message = aMessage;
        try {
          message = procData.transform(userInfo, aMessage);
        } catch (Exception e) {
          Logger.warning(user, this, "after", 
              procData.getSignature() + "exception transforming message: " + aMessage);
        }

        if (Logger.isDebugEnabled()) {        
          Logger.debug(user, this, "after", "TO list:");
          for (String s : tos) {            
            Logger.debug(user, this, "after", "   TO: " + s);
          }
          Logger.debug(user, this, "after", "FROM: " + from);
          Logger.debug(user, this, "after", "SUBJECT: " + subject);
          Logger.debug(user, this, "after", "MESSAGE: " + message);
          Logger.debug(user, this, "after", "TEMPLATE: " + template);
        }

        // validations
        if (tos == null || tos.isEmpty()) {
          Logger.error(user,this,"after", procData.getSignature() + "No to defined");
          outPort = portError;
        } else {
          
          boolean errorAddresses = false;
          for (String mailto : tos) {
            try {
              new InternetAddress(mailto, true);
            }
            catch (AddressException ae) {
              errorAddresses = true;
              Logger.error(user,this,"after", procData.getSignature() + "Invalid to address: " + mailto);
            }
          }
          if (errorAddresses) {
            outPort = portError;
          }
          if (!hasTemplate(template)) {
            // if no template defined, subject, message and from have to be defined
            // otherwise leave it probably for the template
            if (from == null || from.equals("")) {
              Logger.error(user,this,"after",procData.getSignature() + "No from defined");
              outPort = portError;
            } else if (subject == null || subject.equals("")) {
              Logger.error(user,this,"after",procData.getSignature() + "No subject defined");
              outPort = portError;
            } else if (message == null || message.equals("")) {
              Logger.error(user,this,"after",procData.getSignature() + "No message defined");
              outPort = portError;
            }
          }

          if(!outPort.equals(portError)) {
            Email email = null;

            if (hasTemplate(template)) {
              EmailTemplate etemp = EmailManager.getEmailTemplate(userInfo, template);
              if (etemp != null) {
                Hashtable<String,String> htProps = new Hashtable<String,String>();

                // default variables
                if (from != null) htProps.put("from", from);
                if (subject != null) htProps.put("subject", subject);
                if (message != null) htProps.put("message", message);
                htProps.put("app_host", Const.APP_HOST);
                htProps.put("app_port", String.valueOf(Const.APP_PORT));

                // process variables
                // XXX list variables needed???
                for (String sName : procData.getSimpleVariableNames()) {
                  if (htProps.containsKey(sName)) continue;
                  String sValue = procData.getFormatted(sName);
                  if (sValue == null)
                    sValue = "";
                  htProps.put(sName, sValue);
                }

                email = EmailManager.buildEmail(htProps, etemp);
              }
            }

            if (email == null) {
              email = new Email();
              email.setFrom(from);
              email.setSubject(subject);
              email.setMsgText(message);
            }

            if (email != null) {              
              for (String mailto : tos) {           
                email.resetTo();
                email.setTo(mailto);
                email.setCallingProcess(procData.getProcessHeader());
                
                if (email.sendMsg()) {
                  logMsg.append("Mail sent To: " + mailto + ";");
                  Logger.info(user,this,"after",procData.getSignature() + "email sent to " + mailto);
                } else {
                  Logger.error(user,this,"after",procData.getSignature() + "email NOT sent to " + mailto);
                }
              }
              outPort = portSuccess;
            } else {
              Logger.error(user,this,"after",procData.getSignature() + "email is null");
              outPort = portError;
            }
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
        Logger.error(user,this,"after",
            procData.getSignature() + "caught exception: " + e.getMessage(), e);
        outPort = portError;
      }
    }

    logMsg.append("Using '" + outPort.getName() + "';");
    Logger.logFlowState(userInfo, procData, this, logMsg.toString());
    return outPort;
  }

  private boolean hasTemplate(String template) {
    if (template == null || template.equals("") || template.equals(_sSELECT) ||
        !template.endsWith(".et")) {
      return false;
    }
    return true;
  }

  public String getDescription (UserInfoInterface userInfo, ProcessData procData) {
    // TODO: support for block messages for default description
    return this.getDesc(userInfo, procData, true, "Email");
  }

  public String getResult (UserInfoInterface userInfo, ProcessData procData) {
    // TODO: support for block messages for default result
    return this.getDesc(userInfo, procData, false, "Email Enviado");
  }

  public String getUrl (UserInfoInterface userInfo, ProcessData procData) {
    return "";
  }
}
