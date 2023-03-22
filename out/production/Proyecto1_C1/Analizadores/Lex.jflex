package Analizadores;
import java_cup.runtime.Symbol;
import java.util.ArrayList;


%%

%{
public static ArrayList<Errores> errores= new ArrayList<>();
    //Código de usuario
%}

%class Lexico
%cup
%public
%line
%column
%char
%unicode


//SimboloLenguaje
RCONJ = "CONJ"
DOS_PUNTOS= ":"
COMA = ","
PALITO = "~"
LLAVE_ABRE= "{"
LLAVE_CIERRA= "}"
PUNTO = "."
OR = "|"
ASTERISCO= "*"
SUMA = "+"
PREGUNTA= "?"
PORCENTAJE = "%"
PUNTO_COMA = ";"
COMILLA = \"


//ExpresionRegular
ESCAPADO = "\\n" | "\\\"" | "\\\'"
NESCAPADO = [^\'\"]
SPACE = [\ \t\r\n]+
COMENTARIO_MAS= "<!"[^!]*"!>"
COMENTARIO = "//" .*
NOMBRE_M = [A-Z]
NOMBRE = [a-z]
ENTERO  = [0-9]
ASCII = [ -/:-@\[-`{-}]
CADENA = \" ([^\"] | "\\\"") + \"
CARACTER = (\" {NESCAPADO} \" ) | {ESCAPADO}
IDENTIFICADOR = [a-zA-Z_][a-zA-Z0-9_]+
FLECHA = "-" {SPACE}* ">"


%%
<YYINITIAL> {COMENTARIO_MAS}    { /*Comentario mas de una linea ignorado*/ }
<YYINITIAL> {COMENTARIO}        { /*Comentario de una linea ignorado*/ }
<YYINITIAL> {SPACE}             { /*Espacios en blanco, ignorados*/ }
<YYINITIAL> {COMILLA}           { /* ignorar las comillas*/}
<YYINITIAL> {RCONJ}             {  return new Symbol(sym.RCONJ, yyline, yycolumn,yytext());  }
<YYINITIAL> {DOS_PUNTOS}        {  return new Symbol(sym.DOS_PUNTOS, yyline, yycolumn,yytext());  }
<YYINITIAL> {FLECHA}            {  return new Symbol(sym.FLECHA, yyline, yycolumn,yytext());  }
<YYINITIAL> {COMA}              {  return new Symbol(sym.COMA, yyline, yycolumn,yytext());  }
<YYINITIAL> {PALITO}            {  return new Symbol(sym.PALITO, yyline, yycolumn,yytext());  }
<YYINITIAL> {LLAVE_ABRE}        {  return new Symbol(sym.LLAVE_ABRE, yyline, yycolumn,yytext());  }
<YYINITIAL> {LLAVE_CIERRA}      {  return new Symbol(sym.LLAVE_CIERRA, yyline, yycolumn,yytext());  }
<YYINITIAL> {PUNTO}             {  return new Symbol(sym.PUNTO, yyline, yycolumn,yytext());  }
<YYINITIAL> {OR}                {  return new Symbol(sym.OR, yyline, yycolumn,yytext());  }
<YYINITIAL> {ASTERISCO}         {  return new Symbol(sym.ASTERISCO, yyline, yycolumn,yytext());  }
<YYINITIAL> {SUMA}              {  return new Symbol(sym.SUMA, yyline, yycolumn,yytext());  }
<YYINITIAL> {PREGUNTA}          {  return new Symbol(sym.PREGUNTA, yyline, yycolumn,yytext());  }
<YYINITIAL> {PORCENTAJE}        {  return new Symbol(sym.PORCENTAJE, yyline, yycolumn,yytext());  }
<YYINITIAL> {PUNTO_COMA}        {  return new Symbol(sym.PUNTO_COMA, yyline, yycolumn,yytext());  }
<YYINITIAL> {IDENTIFICADOR}     {  return new Symbol(sym.IDENTIFICADOR, yyline, yycolumn,yytext());   }
<YYINITIAL> {NOMBRE}            {  return new Symbol(sym.NOMBRE, yyline, yycolumn,yytext());   }
<YYINITIAL> {CARACTER}          {  return new Symbol(sym.CARACTER, yyline, yycolumn,yytext());   }
<YYINITIAL> {CADENA}            {  return new Symbol(sym.CADENA, yyline, yycolumn,yytext());   }
<YYINITIAL> {NOMBRE_M}          {  return new Symbol(sym.NOMBRE_M, yyline, yycolumn,yytext());  }
<YYINITIAL> {ENTERO}            {  return new Symbol(sym.ENTERO, yyline, yycolumn,yytext());   }
<YYINITIAL> {ASCII}             {  return new Symbol(sym.ASCII, yyline, yycolumn,yytext());   }


<YYINITIAL> . {
        String errLex = "Error léxico : '"+yytext()+"' en la línea: "+(yyline+1)+" y columna: "+(yycolumn+1);
        errores.add(new Errores("Lexico", "El caracter: "+yytext()+" no pertenece al lenguaje",(yyline+1) , (yycolumn+1)));
        System.out.println(errLex);
}