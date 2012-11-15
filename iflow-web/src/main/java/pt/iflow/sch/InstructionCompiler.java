/* Generated By:JavaCC: Do not edit this line. InstructionCompiler.java */
package pt.iflow.sch;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import pt.iflow.api.sch.InstructionCompilerConstants;
import pt.iflow.api.utils.Logger;

public class InstructionCompiler implements InstructionCompilerConstants {
  static java.util.Stack argStack = new java.util.Stack();
  static java.util.HashSet varset = new java.util.HashSet();
  static boolean javascript = false;
  static boolean javascriptNS = false;
  static boolean compile = false;
  static boolean dynamic = false;
  static boolean array = false;
  static Connection dbNS;
  static int interfaceidNS;
  static String nome_campoNS;


  public static String toHTML (String s)
  {
    String newmesg = "";
    int i = 0, j;

    while (i < s.length()) {
      j = s.indexOf('<',i);
      if (j == -1) {
        newmesg = newmesg + s.substring(i);
        break;
      }
      else {
        newmesg = newmesg + s.substring(i,j) + "&lt;";
      }
      i = j + 1;
    }
    s = newmesg;
    newmesg = "";

    i = 0;
    while (i < s.length()) {
      j = s.indexOf('>',i);
      if (j == -1) {
        newmesg = newmesg + s.substring(i);
        break;
      }
      else {
        newmesg = newmesg + s.substring(i,j) + "&gt;";
      }
      i = j + 1;
    }

    return newmesg;
  }


  public static boolean checkExpression (String s)
  {
    javascript = false;
    ReInit (new StringReader(s));
    try {
      expression();
      return true;
    } catch (ParseException pe) {
      return false;
    }
  }

  public static boolean checkCondition (String s)
  {
    javascript = false;
    ReInit (new StringReader(s));
    try {
      condition();
      return true;
    } catch (ParseException pe) {
      return false;
    }
  }

  public static String compileExpression (String s)
  {
     javascript = false;
     compile = true;
     try {
       ReInit (new StringReader(s));
       expression();
       return (String) argStack.pop();
     }
     catch (ParseException pe) {
       return null;
     }
  }

  public static String compileCondition (String s)
  {
     javascript = false;
     compile = true;
     try {
       ReInit (new StringReader(s));
       condition();
       return (String) argStack.pop();
     }
     catch (ParseException pe) {
       return null;
     }
  }

  public static String analyzeExpression (String s)
  {
     javascript = false;
     compile = false;
     try {
       ReInit (new StringReader(s));
       expression();
       return (String) argStack.pop();
     }
     catch (ParseException pe) {
       return null;
     }
  }

  public static String analyzeCondition (String s)
  {
     javascript = false;
     compile = false;
     try {
       ReInit (new StringReader(s));
       condition();
       return (String) argStack.pop();
     }
     catch (ParseException pe) {
       return null;
     }
  }

  public static String compileJavascriptCondition (String s)
  {
    javascript = true;
    javascriptNS = false;
    compile = false;
    dynamic = false;
    array = false;
    if (s == null || s.equals(""))
      return "false";
    javascript = true;
    try {
      ReInit (new StringReader(s));
      condition();
      return (String) argStack.pop();
    }
    catch (ParseException pe) {
      return "false";
    }
  }

  public static String compileJavascriptConditionNS (Connection db, int interfaceid, String nome_campo, String s)
  {
    dbNS = db;
    interfaceidNS = interfaceid;
    nome_campoNS = nome_campo;
    javascript = true;
    javascriptNS = true;
    compile = false;
    dynamic = false;
    array = false;
    if (s == null || s.equals(""))
      return "false";
    javascript = true;
    try {
      ReInit (new StringReader(s));
      condition();
      return (String) argStack.pop();
    }
    catch (ParseException pe) {
      return "false";
    }
  }

  public static String compileDynamicCondition (String s)
  {
    javascript = false;
    compile = false;
    dynamic = true;
    array = false;

    if (s == null || s.equals(""))
      return "false";
    try {
      ReInit (new StringReader(s));
      condition();
      return (String) argStack.pop();
    }
    catch (ParseException pe) {
      return "false";
    }
  }

  public static String compileDynamicExpression (String s)
  {
     javascript = false;
     compile = false;
     dynamic = true;
     array = false;
     try {
       ReInit (new StringReader(s));
       expression();
       return (String) argStack.pop();
     }
     catch (ParseException pe) {
       return null;
     }
  }

  public static String compileDynamicArrayExpression (String s)
  {
     javascript = false;
     compile = false;
     dynamic = true;
     array = true;
     try {
       ReInit (new StringReader(s));
       expression();
       return (String) argStack.pop();
     }
     catch (ParseException pe) {
       return null;
     }
  }

  public static String compile (String s)
  {
     javascript = false;
     compile = true;
     try {
       ReInit (new StringReader(s));
       expression();
       return (String) argStack.pop();
     }
     catch (ParseException pe) {
     }
     try {
       ReInit (new StringReader(s));
       condition();
       return (String) argStack.pop();
     }
     catch (ParseException pe) {
       return null;
     }
  }

  public static String analyze (String s)
  {
     javascript = false;
     compile = false;
     try {
       ReInit (new StringReader(s));
       expression();
       return (String) argStack.pop();
     }
     catch (ParseException pe) {
     }
     try {
       ReInit (new StringReader(s));
       condition();
       return (String) argStack.pop();
     }
     catch (ParseException pe) {
       return null;
     }
  }


  public static void clearVarSet ()
  {
    varset.clear();
  }

  public static java.util.HashSet getVarSet ()
  {
    return varset;
  }

  static final public void condition() throws ParseException {
    cond();
    jj_consume_token(0);
  }

  static final public void cond() throws ParseException {
 Token x;
    unary_cond();
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case AND:
      case OR:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case AND:
        x = jj_consume_token(AND);
        break;
      case OR:
        x = jj_consume_token(OR);
        break;
      default:
        jj_la1[1] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      unary_cond();
     String a = (String) argStack.pop();
     String b = (String) argStack.pop();
     if (x.kind == AND)
       argStack.push(b + " && " + a);
     else if (x.kind == OR)
       argStack.push(b + " || " + a);
    }
  }

  static final public void unary_cond() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NOT:
      jj_consume_token(NOT);
      simple_cond();
     String a = (String) argStack.pop();
     argStack.push ("!" + a);
      break;
    case MINUS:
    case CONSTANT:
    case ID:
    case PAR_OPEN:
      simple_cond();
      break;
    case VERDADEIRO:
      jj_consume_token(VERDADEIRO);
                   if (compile || javascript)
               argStack.push ("true");
             else
               argStack.push ("verdadeiro");
      break;
    case FALSO:
      jj_consume_token(FALSO);
            if (compile || javascript || dynamic)
               argStack.push ("false");
             else
               argStack.push ("falso");
      break;
    case TRUE:
      jj_consume_token(TRUE);
             if (compile || javascript)
               argStack.push ("true");
             else
               argStack.push ("verdadeiro");
      break;
    case FALSE:
      jj_consume_token(FALSE);
            if (compile || javascript)
               argStack.push ("false");
             else
               argStack.push ("false");
      break;
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void simple_cond() throws ParseException {
 Token x;
    if (jj_2_1(10)) {
      sum();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EQUAL:
        x = jj_consume_token(EQUAL);
        break;
      case NON_EQUAL:
        x = jj_consume_token(NON_EQUAL);
        break;
      case GREATER:
        x = jj_consume_token(GREATER);
        break;
      case GREATER_OR_EQUAL:
        x = jj_consume_token(GREATER_OR_EQUAL);
        break;
      case SMALLER:
        x = jj_consume_token(SMALLER);
        break;
      case SMALLER_OR_EQUAL:
        x = jj_consume_token(SMALLER_OR_EQUAL);
        break;
      default:
        jj_la1[3] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      sum();
     String a = (String) argStack.pop();
     String b = (String) argStack.pop();
     argStack.push(b + x.image + a);
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PAR_OPEN:
        jj_consume_token(PAR_OPEN);
        cond();
        jj_consume_token(PAR_CLOSE);
    String c = (String) argStack.pop();
    argStack.push("(" + c + ")");
        break;
      default:
        jj_la1[4] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  static final public void expression() throws ParseException {
    sum();
    jj_consume_token(0);
  }

  static final public void sum() throws ParseException {
 Token x;
    term();
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS:
      case MINUS:
        ;
        break;
      default:
        jj_la1[5] = jj_gen;
        break label_2;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS:
        x = jj_consume_token(PLUS);
        break;
      case MINUS:
        x = jj_consume_token(MINUS);
        break;
      default:
        jj_la1[6] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      term();
          String a = (String) argStack.pop();
          String b = (String) argStack.pop();
          if ( x.kind == PLUS )
            argStack.push(b + "+" + a);
          else
            argStack.push(b + "-" + a);
    }
  }

  static final public void term() throws ParseException {
 Token x;
    exp();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MULTIPLY:
      case DIVIDE:
        ;
        break;
      default:
        jj_la1[7] = jj_gen;
        break label_3;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MULTIPLY:
        x = jj_consume_token(MULTIPLY);
        break;
      case DIVIDE:
        x = jj_consume_token(DIVIDE);
        break;
      default:
        jj_la1[8] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      exp();
    String a = (String) argStack.pop();
    String b = (String) argStack.pop();
    if ( x.kind == MULTIPLY )
      argStack.push(b + "*" + a);
    else
      argStack.push(b + "/" + a);
    }
  }

  static final public void exp() throws ParseException {
    unary();
    label_4:
    while (true) {
      if (jj_2_2(2147483647)) {
        ;
      } else {
        break label_4;
      }
      jj_consume_token(EXP);
      exp();
    String a = (String) argStack.pop();
    String b = (String) argStack.pop();
    if (compile) {
      argStack.push("Math.pow(" + b + "," + a + ")");
    }
    else {
      argStack.push(b + "^" + a);
    }
    }
  }

  static final public void unary() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MINUS:
      jj_consume_token(MINUS);
      element();
    String a = (String) argStack.pop();
    argStack.push("-" + a);
      break;
    case CONSTANT:
    case ID:
    case PAR_OPEN:
      element();
      break;
    default:
      jj_la1[9] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void element() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case CONSTANT:
      jj_consume_token(CONSTANT);
    try {
      Double.valueOf(token.image);
      if (javascript && javascriptNS) {
        try{
          int objectid = 0;
          Statement st = dbNS.createStatement();
          ResultSet rs =  st.executeQuery("select * from campos where variable='" + nome_campoNS + "' AND interfaceid = " + interfaceidNS + " ");
          if (rs.next()) {
            objectid = rs.getInt("objectid");
          }
          rs =  st.executeQuery("select * from valores where objectid="+ objectid + " AND value = " + token.image +" AND interfaceid = " + interfaceidNS + " ");
          if (rs.next()) {
           argStack.push("" + (rs.getInt("value_pos")-1));
          }
        }
        catch(SQLException sqle){
          Logger.error("", "", "", sqle.getMessage(), sqle);
        }
      }
      else{
        argStack.push(token.image);
      }
    }
    catch (NumberFormatException ee) {
      argStack.push("Double.NaN");
    }
      break;
    case ID:
      jj_consume_token(ID);
         argStack.push(token.image);
      var();
    String h = (String) argStack.pop();
    if (h.equals("NaN"))
      h = "Double.NaN";
    argStack.push(h);
      break;
    case PAR_OPEN:
      jj_consume_token(PAR_OPEN);
      sum();
      jj_consume_token(PAR_CLOSE);
    String a = (String) argStack.pop();
    argStack.push("(" + a + ")");
      break;
    default:
      jj_la1[10] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void var() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case RECT_OPEN:
      jj_consume_token(RECT_OPEN);
      jj_consume_token(CONSTANT);
                           argStack.push(token.image);
      jj_consume_token(RECT_CLOSE);
    if (javascript) {
    }
    else {
      if (dynamic && array) {
        String g = (String) argStack.pop();
        String f = (String) argStack.pop();
        argStack.push ("uc.getValue(\"" + f + "\"," + g + ")");
      }
      else if (dynamic && !array) {
        String g = (String) argStack.pop();
        String f = (String) argStack.pop();
        argStack.push ("uc.getValue(\"" + f + "\"," + g + ")");
      }
      else if (compile) {
        String g = (String) argStack.pop();
        String f = (String) argStack.pop();
        argStack.push ("uc.getValue(\"" + f + "\"," + g + ")");
      }
      else {
        String g = (String) argStack.pop();
        String f = (String) argStack.pop();
        argStack.push (f + "[" + g + "]");
      }
    }
      break;
    default:
      jj_la1[11] = jj_gen;
    String k = (String) argStack.pop();
    if (javascript) {
      if (!javascriptNS) {
        argStack.push ("document.dados." + k + ".value");
      }
      else{
        nome_campoNS = k;
        argStack.push ("document.dados." + k + ".selectedIndex");
      }
    }
    else {
      if (dynamic) {
        if (array) {
          argStack.push ("uc.getValue(\"" + k + "\",i)");
        }
        else {
          argStack.push ("uc.getValue(\"" + k + "\")");
        }
      }
      else if (compile) {
        argStack.push ("v" + k);
        varset.add (new String("v" + k));
      }
      else {
        argStack.push (k);
      }
    }
    }
  }

  static final private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    boolean retval = !jj_3_1();
    jj_save(0, xla);
    return retval;
  }

  static final private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    boolean retval = !jj_3_2();
    jj_save(1, xla);
    return retval;
  }

  static final private boolean jj_3_1() {
    if (jj_3R_5()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_6()) {
    jj_scanpos = xsp;
    if (jj_3R_7()) {
    jj_scanpos = xsp;
    if (jj_3R_8()) {
    jj_scanpos = xsp;
    if (jj_3R_9()) {
    jj_scanpos = xsp;
    if (jj_3R_10()) {
    jj_scanpos = xsp;
    if (jj_3R_11()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    if (jj_3R_5()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  static final private boolean jj_3R_17() {
    if (jj_scan_token(MINUS)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  static final private boolean jj_3R_12() {
    if (jj_3R_14()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    Token xsp;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3R_15()) { jj_scanpos = xsp; break; }
      if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    }
    return false;
  }

  static final private boolean jj_3R_29() {
    if (jj_scan_token(RECT_OPEN)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    if (jj_scan_token(CONSTANT)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    if (jj_scan_token(RECT_CLOSE)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  static final private boolean jj_3R_28() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_29()) {
    jj_scanpos = xsp;
    if (jj_3R_30()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  static final private boolean jj_3R_25() {
    if (jj_scan_token(CONSTANT)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  static final private boolean jj_3R_24() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_25()) {
    jj_scanpos = xsp;
    if (jj_3R_26()) {
    jj_scanpos = xsp;
    if (jj_3R_27()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  static final private boolean jj_3R_7() {
    if (jj_scan_token(NON_EQUAL)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  static final private boolean jj_3R_16() {
    if (jj_scan_token(PLUS)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  static final private boolean jj_3R_23() {
    if (jj_3R_24()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  static final private boolean jj_3R_13() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_16()) {
    jj_scanpos = xsp;
    if (jj_3R_17()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    if (jj_3R_12()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  static final private boolean jj_3_2() {
    if (jj_scan_token(EXP)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  static final private boolean jj_3R_27() {
    if (jj_scan_token(PAR_OPEN)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    if (jj_3R_5()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    if (jj_scan_token(PAR_CLOSE)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  static final private boolean jj_3R_5() {
    if (jj_3R_12()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    Token xsp;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3R_13()) { jj_scanpos = xsp; break; }
      if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    }
    return false;
  }

  static final private boolean jj_3R_22() {
    if (jj_scan_token(MINUS)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    if (jj_3R_24()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  static final private boolean jj_3R_18() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_22()) {
    jj_scanpos = xsp;
    if (jj_3R_23()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  static final private boolean jj_3R_11() {
    if (jj_scan_token(SMALLER_OR_EQUAL)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  static final private boolean jj_3R_6() {
    if (jj_scan_token(EQUAL)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  static final private boolean jj_3R_30() {
    return false;
  }

  static final private boolean jj_3R_26() {
    if (jj_scan_token(ID)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    if (jj_3R_28()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  static final private boolean jj_3R_21() {
    if (jj_scan_token(DIVIDE)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  static final private boolean jj_3R_19() {
    if (jj_scan_token(EXP)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    if (jj_3R_14()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  static final private boolean jj_3R_9() {
    if (jj_scan_token(GREATER_OR_EQUAL)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  static final private boolean jj_3R_10() {
    if (jj_scan_token(SMALLER)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  static final private boolean jj_3R_14() {
    if (jj_3R_18()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    Token xsp;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3R_19()) { jj_scanpos = xsp; break; }
      if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    }
    return false;
  }

  static final private boolean jj_3R_20() {
    if (jj_scan_token(MULTIPLY)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  static final private boolean jj_3R_8() {
    if (jj_scan_token(GREATER)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  static final private boolean jj_3R_15() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_20()) {
    jj_scanpos = xsp;
    if (jj_3R_21()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    if (jj_3R_14()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  static private boolean jj_initialized_once = false;
  static public InstructionCompilerTokenManager token_source;
  static ASCII_CharStream jj_input_stream;
  static public Token token, jj_nt;
  static private int jj_ntk;
  static private Token jj_scanpos, jj_lastpos;
  static private int jj_la;
  static public boolean lookingAhead = false;
  //static private boolean jj_semLA;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[12];
  static final private int[] jj_la1_0 = {0x60000,0x60000,0x51f80040,0x1f800,0x40000000,0x60,0x60,0x180,0x180,0x51000040,0x51000000,0x0,};
  static final private int[] jj_la1_1 = {0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x1,};
  static final private JJCalls[] jj_2_rtns = new JJCalls[2];
  static private boolean jj_rescan = false;
  static private int jj_gc = 0;

  public InstructionCompiler(java.io.InputStream stream) {
    if (jj_initialized_once) {
      Logger.debug("", this, "", "ERROR: Second call to constructor of static parser.  You must");
      Logger.debug("", this, "", "       either use ReInit() or set the JavaCC option STATIC to false");
      Logger.debug("", this, "", "       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new ASCII_CharStream(stream, 1, 1);
    token_source = new InstructionCompilerTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 12; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  static public void ReInit(java.io.InputStream stream) {
    ASCII_CharStream.ReInit(stream, 1, 1);
    InstructionCompilerTokenManager.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 12; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public InstructionCompiler(java.io.Reader stream) {
    if (jj_initialized_once) {
      Logger.debug("", this, "", "ERROR: Second call to constructor of static parser.  You must");
      Logger.debug("", this, "", "       either use ReInit() or set the JavaCC option STATIC to false");
      Logger.debug("", this, "", "       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new ASCII_CharStream(stream, 1, 1);
    token_source = new InstructionCompilerTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 12; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  static public void ReInit(java.io.Reader stream) {
    ASCII_CharStream.ReInit(stream, 1, 1);
    InstructionCompilerTokenManager.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 12; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public InstructionCompiler(InstructionCompilerTokenManager tm) {
    if (jj_initialized_once) {
      Logger.debug("", this, "", "ERROR: Second call to constructor of static parser.  You must");
      Logger.debug("", this, "", "       either use ReInit() or set the JavaCC option STATIC to false");
      Logger.debug("", this, "", "       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 12; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public void ReInit(InstructionCompilerTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 12; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  static final private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = InstructionCompilerTokenManager.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static final private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = InstructionCompilerTokenManager.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    return (jj_scanpos.kind != kind);
  }

  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = InstructionCompilerTokenManager.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

  static final public Token getToken(int index) {
    Token t = lookingAhead ? jj_scanpos : token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = InstructionCompilerTokenManager.getNextToken();
    }
    return t;
  }

  static final private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=InstructionCompilerTokenManager.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.Vector jj_expentries = new java.util.Vector();
  static private int[] jj_expentry;
  static private int jj_kind = -1;
  static private int[] jj_lasttokens = new int[100];
  static private int jj_endpos;

  static private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      boolean exists = false;
      for (java.util.Enumeration enumer = jj_expentries.elements(); enumer.hasMoreElements();) {
        int[] oldentry = (int[])(enumer.nextElement());
        if (oldentry.length == jj_expentry.length) {
          exists = true;
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              exists = false;
              break;
            }
          }
          if (exists) break;
        }
      }
      if (!exists) jj_expentries.addElement(jj_expentry);
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  static final public ParseException generateParseException() {
    jj_expentries.removeAllElements();
    boolean[] la1tokens = new boolean[35];
    for (int i = 0; i < 35; i++) {
      la1tokens[i] = false;
    }
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 12; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 35; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.addElement(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.elementAt(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  static final public void enable_tracing() {
  }

  static final public void disable_tracing() {
  }

  static final private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 2; i++) {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
          }
        }
        p = p.next;
      } while (p != null);
    }
    jj_rescan = false;
  }

  static final private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
