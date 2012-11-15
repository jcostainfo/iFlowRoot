/*
Copyright (c) 2010, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.com/yui/license.html
version: 2.8.1pr1r2
*/
(function(){var B=YAHOO.util.Dom,H=YAHOO.lang,X="yui-pb",Z=X+"-mask",W=X+"-bar",V=X+"-anim",M=X+"-tl",K=X+"-tr",J=X+"-bl",F=X+"-br",G="width",S="height",L="minValue",U="maxValue",I="value",A="anim",T="direction",D="ltr",P="rtl",d="ttb",O="btt",E="barEl",C="maskEl",R="ariaTextTemplate",Y="start",c="progress",Q="complete";var N=function(b){N.superclass.constructor.call(this,document.createElement("div"),b);this._init(b);};YAHOO.widget.ProgressBar=N;N.MARKUP=['<div class="',W,'"></div><div class="',Z,'"><div class="',M,'"></div><div class="',K,'"></div><div class="',J,'"></div><div class="',F,'"></div></div>'].join("");H.extend(N,YAHOO.util.Element,{_init:function(b){},initAttributes:function(f){N.superclass.initAttributes.call(this,f);this.set("innerHTML",N.MARKUP);this.addClass(X);var e,b=["id",G,S,"class","style"];while((e=b.pop())){if(e in f){this.set(e,f[e]);}}this.setAttributeConfig(E,{readOnly:true,value:this.getElementsByClassName(W)[0]});this.setAttributeConfig(C,{readOnly:true,value:this.getElementsByClassName(Z)[0]});this.setAttributeConfig(T,{value:D,validator:function(g){if(this._rendered){return false;}switch(g){case D:case P:case d:case O:return true;default:return false;}},method:function(g){this._barSizeFunction=this._barSizeFunctions[this.get(A)?1:0][g];}});this.setAttributeConfig(U,{value:100,validator:H.isNumber,method:function(g){this.get("element").setAttribute("aria-valuemax",g);if(this.get(I)>g){this.set(I,g);}}});this.setAttributeConfig(L,{value:0,validator:H.isNumber,method:function(g){this.get("element").setAttribute("aria-valuemin",g);if(this.get(I)<g){this.set(I,g);}}});this.setAttributeConfig(G,{getter:function(){return this.getStyle(G);},method:this._widthChange});this.setAttributeConfig(S,{getter:function(){return this.getStyle(S);},method:this._heightChange});this.setAttributeConfig(R,{value:"{value}"});this.setAttributeConfig(I,{value:0,validator:function(g){return H.isNumber(g)&&g>=this.get(L)&&g<=this.get(U);},method:this._valueChange});this.setAttributeConfig(A,{validator:function(g){return !!YAHOO.util.Anim;},setter:this._animSetter});},render:function(e,f){if(this._rendered){return;}this._rendered=true;var g=this.get(T);this.addClass(X);this.addClass(X+"-"+g);var b=this.get("element");b.tabIndex=0;b.setAttribute("role","progressbar");b.setAttribute("aria-valuemin",this.get(L));b.setAttribute("aria-valuemax",this.get(U));this.appendTo(e,f);this._barSizeFunction=this._barSizeFunctions[0][g];this.redraw();this._previousValue=this.get(I);this._fixEdges();if(this.get(A)){this._barSizeFunction=this._barSizeFunctions[1][g];}this.on("minValueChange",this.redraw);this.on("maxValueChange",this.redraw);return this;},redraw:function(){this._recalculateConstants();this._valueChange(this.get(I));},destroy:function(){this.set(A,false);this.unsubscribeAll();var b=this.get("element");if(b.parentNode){b.parentNode.removeChild(b);}},_previousValue:0,_barSpace:100,_barFactor:1,_rendered:false,_barSizeFunction:null,_heightChange:function(b){if(H.isNumber(b)){b+="px";}this.setStyle(S,b);this._fixEdges();this.redraw();},_widthChange:function(b){if(H.isNumber(b)){b+="px";}this.setStyle(G,b);this._fixEdges();this.redraw();},_fixEdges:function(){if(!this._rendered||YAHOO.env.ua.ie||YAHOO.env.ua.gecko){return;}var g=this.get(C),i=B.getElementsByClassName(M,undefined,g)[0],f=B.getElementsByClassName(K,undefined,g)[0],h=B.getElementsByClassName(J,undefined,g)[0],e=B.getElementsByClassName(F,undefined,g)[0],b=(parseInt(B.getStyle(g,S),10)-parseInt(B.getStyle(i,S),10))+"px";B.setStyle(h,S,b);B.setStyle(e,S,b);b=(parseInt(B.getStyle(g,G),10)-parseInt(B.getStyle(i,G),10))+"px";B.setStyle(f,G,b);B.setStyle(e,G,b);},_recalculateConstants:function(){var b=this.get(E);switch(this.get(T)){case D:case P:this._barSpace=parseInt(this.get(G),10)-(parseInt(B.getStyle(b,"marginLeft"),10)||0)-(parseInt(B.getStyle(b,"marginRight"),10)||0);break;case d:case O:this._barSpace=parseInt(this.get(S),10)-(parseInt(B.getStyle(b,"marginTop"),10)||0)-(parseInt(B.getStyle(b,"marginBottom"),10)||0);break;}this._barFactor=this._barSpace/(this.get(U)-(this.get(L)||0))||1;},_animSetter:function(g){var f,b=this.get(E);if(g){if(g instanceof YAHOO.util.Anim){f=g;}else{f=new YAHOO.util.Anim(b);}f.onTween.subscribe(this._animOnTween,this,true);f.onComplete.subscribe(this._animComplete,this,true);var h=f.setAttribute,e=this;switch(this.get(T)){case O:f.setAttribute=function(i,k,j){k=Math.round(k);h.call(this,i,k,j);B.setStyle(b,"top",(e._barSpace-k)+"px");};break;case P:f.setAttribute=function(i,k,j){k=Math.round(k);h.call(this,i,k,j);B.setStyle(b,"left",(e._barSpace-k)+"px");};break;}}else{f=this.get(A);if(f){f.onTween.unsubscribeAll();f.onComplete.unsubscribeAll();}f=null;}this._barSizeFunction=this._barSizeFunctions[f?1:0][this.get(T)];return f;},_animComplete:function(){var b=this.get(I);this._previousValue=b;this.fireEvent(c,b);this.fireEvent(Q,b);B.removeClass(this.get(E),V);},_animOnTween:function(b,e){var f=Math.floor(this._tweenFactor*e[0].currentFrame+this._previousValue);this.fireEvent(c,f);},_valueChange:function(g){var f=this.get(A),b=Math.floor((g-this.get(L))*this._barFactor),e=this.get(E);this._setAriaText(g);if(this._rendered){if(f){f.stop();if(f.isAnimated()){f._onComplete.fire();}}this.fireEvent(Y,this._previousValue);this._barSizeFunction(g,b,e,f);}},_setAriaText:function(e){var b=this.get("element"),f=H.substitute(this.get(R),{value:e,minValue:this.get(L),maxValue:this.get(U)});b.setAttribute("aria-valuenow",e);b.setAttribute("aria-valuetext",f);}});var a=[{},{}];N.prototype._barSizeFunctions=a;a[0][D]=function(g,b,e,f){B.setStyle(e,G,b+"px");this.fireEvent(c,g);this.fireEvent(Q,g);};a[0][P]=function(g,b,e,f){B.setStyle(e,G,b+"px");B.setStyle(e,"left",(this._barSpace-b)+"px");this.fireEvent(c,g);this.fireEvent(Q,g);};a[0][d]=function(g,b,e,f){B.setStyle(e,S,b+"px");this.fireEvent(c,g);this.fireEvent(Q,g);};a[0][O]=function(g,b,e,f){B.setStyle(e,S,b+"px");B.setStyle(e,"top",(this._barSpace-b)+"px");
this.fireEvent(c,g);this.fireEvent(Q,g);};a[1][D]=function(g,b,e,f){B.addClass(e,V);this._tweenFactor=(g-this._previousValue)/f.totalFrames/f.duration;f.attributes={width:{to:b}};f.animate();};a[1][P]=a[1][D];a[1][d]=function(g,b,e,f){B.addClass(e,V);this._tweenFactor=(g-this._previousValue)/f.totalFrames/f.duration;f.attributes={height:{to:b}};f.animate();};a[1][O]=a[1][d];})();YAHOO.register("progressbar",YAHOO.widget.ProgressBar,{version:"2.8.1pr1r2",build:"9"});