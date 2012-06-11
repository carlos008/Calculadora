
public class Sintaxis{
  public String expr;
  
  //Valida la expresi�n algebraica 

  public boolean check(String dat){
    boolean correct=true;
    expr=limpiarEspacios(dat);
    // enpezando a comprobar sintaxis
    if(vacia() && correct)
      correct=false;
    //**** 1 ***
    if(dosOperadoresSeguidos() && correct)
      correct=false;
    if(parentesisAbreOperador() && correct)
      correct=false;
    if(operadorParentesisCierra()&& correct)
      correct=false;
    if(iniciaOperador() && correct)
      correct=false;
    if(finalizaOperador() && correct)
      correct=false;
    if(parentesisDesbalanceados() && correct)
      correct=false;
    if(parentesisVacio() && correct)
      correct=false;
    if(parentesisCierraNumero() && correct)
      correct=false;
    if(numeroParentesisAbre() && correct)
      correct=false;
    if(doblePuntoNumero() && correct)
      correct=false;
    if(parentesisCierraVariable() && correct)
      correct=false;
    if(variableLuegoPunto() && correct)
      correct=false;
    if(puntoLuegoVariable() && correct)
      correct=false;
    if(numeroLuegoVariable() && correct)
      correct=false;
    //if(variableLuegoNumero() && correct)
      //correct=false;
    if(caracterInvalido() && correct)
      correct=false;
    if(checkFourLetras() && correct)
      correct=false;
    return correct;
  }
  //Retira los espacios de la expresi�n 
  private String limpiarEspacios(String dat){
    String aux="";
    for(int i=0;i<dat.length();i++)
      if(dat.charAt(i)!=' ')
        aux+=dat.charAt(i);
    return aux;
  }
  // comprueba si la cadena esta vacia
  private boolean vacia(){
    return expr.length()==0;
  }
  //1. Dos o m�s operadores est�n seguidos.
  private boolean dosOperadoresSeguidos(){
    boolean band=false;//asumimos q esta bien
    //Va de car�cter en car�cter 
    for(int i = 0;i< expr.length()-1;i++){
      char car1 = expr.charAt(i); //Extrae el caracter actual 
      char car2 = expr.charAt(i+1); //Extrae el siguiente car�cter
      //Compara si el car�cter y el siguiente son operadores, dado el caso retorna true 
      if (car1 == '+' || car1 == '-' || car1 == '*' || car1 == '/' || car1 == '^')
        if (car2 == '+' || car2 == '-' || car2 == '*' || car2 == '/' || car2 == '^')
          band=true;
    }
    return band;
  }
  //2. Un par�ntesis que abre seguido de un operador. Ejemplo: 2-(*3) 
  private boolean parentesisAbreOperador(){
    int i;
    char car, car2;
    boolean band=false;
    //Va de car�cter en car�cter 
    for(i=0;i<expr.length()-1;i++){
      car = expr.charAt(i); //Extrae un car�cter 
      car2 = expr.charAt(i+1); //Extrae el siguiente car�cter
      //Compara si el primer car�cter es par�ntesis que abre y el siguiente es operador 
      if (car == '(') if (car2 == '+' || car2 == '-' || car2 == '*' || car2 == '/' || car2 == '^')
        band = true;
    }
    return band;
  }
  //3. Un operador seguido de un par�ntesis que cierra. Ejemplo: 2-(4+)-7
  private boolean operadorParentesisCierra(){
    boolean band=false;
    int i;
    char car, car2;
    //Va de car�cter en car�cter 
    for(i = 0;i<expr.length()-1;i++){
      car = expr.charAt(i); //Extrae el car�cter actual
      car2 = expr.charAt(i+1); //Extrae el siguiente car�cter
      //Compara si el primer car�cter es operador y el siguiente es par�ntesis que cierra 
      if (car == '+' || car == '-' || car == '*' || car == '/' || car == '^') if (car2 == ')')
        band=true; 
    }
    return band;
  }
  //4. Que empiece con operador +, *, /. Ejemplo: /12-5*2 , *17-4 
  private boolean iniciaOperador(){
    boolean band=false;
    //Extrae el primer car�cter 
    char car = expr.charAt(0);
    //Compara si el primer car�cter es operador 
    if(car == '+' || car == '-' || car == '*' || car == '/' || car == '^') 
      band=true;
    return band;
  }
  //5. Que termine con operador. Ejemplo: 12-67* 2/3- 
  private boolean finalizaOperador(){
    boolean band=false;
    char car = expr.charAt(expr.length()-1);
    //Compara si el ultimo car�cter es operador 
    if(car == '+' || car == '-' || car == '*' || car == '/' || car == '^')
      band=true;
    return band; 
  }
  //6. Que los par�ntesis est�n desbalanceados. Ejemplo: 3-(2*4))
  private boolean parentesisDesbalanceados(){
    boolean band=false;
    Pila pila=new Pila(10);
    //Va de car�cter en car�cter 
    for(int i=0;i<expr.length();i++){
      if(expr.charAt(i)=='(')
        pila.push(expr.charAt(i));
      else if(expr.charAt(i)==')'){
        if(!pila.vacia())
          pila.pop();
        else{
          band=true;
          i=expr.length();
        } 
      }
    }
    if(!pila.vacia())
      band=true;
    return band;
  }
  //7. Que haya par�ntesis vac�o. Ejemplo: 2-()*3 
  public boolean parentesisVacio(){
    boolean band=false;
    //Va de car�cter en car�cter 
    for(int i=0;i<expr.length()-1;i++){
      char car1 = expr.charAt(i); //Extrae un car�cter 
      char car2 = expr.charAt(i+1); //Extrae el siguiente car�cter
      //Compara si el primer car�cter es par�ntesis que abre y el siguiente es par�ntesis que cierra 
      if(car1 == '(' && car2 == ')')
        band=true;
    }
    return band;
  }
  //8. Un par�ntesis que cierra seguido de un n�mero. Ejemplo: (12-4)7-1 
  private boolean parentesisCierraNumero(){
    boolean band=false;
    //Va de car�cter en car�cter 
    for(int i=0;i<expr.length()-1;i++){
      char car1 = expr.charAt(i); //Extrae un car�cter 
      char car2 = expr.charAt(i+1); //Extrae el siguiente car�cter
      //Compara si el primer car�cter es par�ntesis que abre y el siguiente es par�ntesis que cierra
      if(car1 == ')')
        if((car2 >= '0' && car2 <= '9') || car2 == '.') 
          band=true;
    }
    return band;
  }
  //9. Un n�mero seguido de un par�ntesis que abre. Ejemplo: 7-2(5-6)
  private boolean numeroParentesisAbre(){
    boolean band=false;
    //Va de car�cter en car�cter 
    for(int i=0;i<expr.length()-1;i++){
      char car1 = expr.charAt(i); //Extrae un car�cter
      char car2 = expr.charAt(i+1); //Extrae el siguiente car�cter
      //Compara si el primer car�cter es n�mero y el siguiente es par�ntesis que abre 
      if((car1 >= '0' && car1 <= '9') || car1 == '.')
        if(car2 == '(')
          band=true; 
    }
    return band; 
  }
  //10. Doble punto en un n�mero de tipo real. Ejemplo: 3-2..4+1 7-6.46.1+2
  private boolean doblePuntoNumero(){
    boolean band=false;
    int puntos=0;
    //Va de car�cter en car�cter 
    for(int i=0;i<expr.length();i++){
      char car = expr.charAt(i); //Extrae un car�cter
      if((car < '0' || car > '9') && car != '.')
        puntos=0;
      if(car == '.')
        puntos++;
      if(puntos > 1){
        band=true;
        i=expr.length();
      }
    }
    return band;
  }
  //11. Un par�ntesis que cierra seguido de una variable. Ejemplo: (12-4)y-1 
  private boolean parentesisCierraVariable(){
    boolean band=false;
    //Va de car�cter en car�cter
    for(int i=0;i<expr.length()-1;i++){
      char car1 = expr.charAt(i); //Extrae un car�cter
      char car2 = expr.charAt(i+1); //Extrae el siguiente car�cter
      //Compara si el primer car�cter es par�ntesis que cierra y el siguiente es letra 
      if (car1 == ')')
        if (car2 >= 'a' && car2 <= 'z')
          band=true; 
    }
    return band;
  }
  //12. Una variable seguida de un punto. Ejemplo: 4-z.1+3 
  private boolean variableLuegoPunto(){
    boolean band=false;
    for(int i=0;i<expr.length()-1;i++){
      char car1 = expr.charAt(i);
      char car2 = expr.charAt(i+1);
      //Compara si el primer car�cter es letra y el siguiente es punto 
      if (car1 >= 'a' && car1 <= 'z')
        if (car2 == '.')
          band=true; 
    }
    return band;
  }
  //13. Un punto seguido de una variable. Ejemplo: 7-2.p+1
  private boolean puntoLuegoVariable(){
    boolean band=false;
    for(int i=0;i<expr.length()-1;i++){
      char car1 = expr.charAt(i);
      char car2 = expr.charAt(i+1);
      //Compara si el primer car�cter es punto y el siguiente es letra
      if(car2 == '.')
        if(car1 >= 'a' && car1 <= 'z')
          band=true; 
    }
    return band;
  }
  //14. Un n�mero antes de una variable. Ejemplo: 3x+1 
  // Nota: Algebraicamente es aceptable 3x+1 pero entonces vuelve m�s 
  //   complejo un evaluador porque debe saber que 3x+1 es en realidad 3*x+1
  private boolean numeroLuegoVariable(){ 
    boolean band=false;
    //Va de car�cter en car�cter 
    for(int i=0;i<expr.length()-1;i++){ 
      char car1 = expr.charAt(i); //Extrae un car�cter 
      char car2 = expr.charAt(i + 1); //Extrae el siguiente car�cter
      //Compara si el primer car�cter es numero  y el siguiente es letra 
      if(car1>='0' && car1<='9')
        if(car2>='a' && car2<='z') 
          band=true; 
    }
    return band;
  }
  //15. Un n�mero despu�s de una variable. Ejemplo: x21+4 
  /** No utilizado */
  private boolean variableLuegoNumero(){
    boolean band=false;
    for(int i=0;i<expr.length()-1;i++){
      char car1 = expr.charAt(i); //Extrae un car�cter 
      char car2 = expr.charAt(i+1); //Extrae el siguiente car�cter
      //Compara si el primer car�cter es letra y el siguiente es numero 
      if(car1 >= 'a' && car1 <= 'z')
        if(car2 >= '0' && car2 <= '9')
          band=true;
    }
    return band;
  }
  //16. caracteres inv�lidos. Ejemplo: 4+@-1 
  private boolean caracterInvalido(){
    boolean band=false;
    String validos = "abcdefghijklmnopqrstuvwxyz0123456789.+-*/^()";
    //Va de car�cter en car�cter
    for(int i=0;i<expr.length();i++){
      boolean valido=false;
      char car1 = expr.charAt(i);
      for(int j=0;j<validos.length();j++){
        char car2 = validos.charAt(j); 
        if(car1==car2){
          valido=true;
          j=validos.length();
        }
      }
      if(valido==false){
        band=true;
        i=expr.length();
      }
      // System.out.println(car1+" --> "+valido);
      // System.out.println("band --> "+band);
    }
    return band;
  }
  //17. 4 o m�s variables seguidas 
  private boolean checkFourLetras(){
    boolean band=false;
    char car1, car2, car3, car4;
    for(int i = 0;i<expr.length()-3;i++){
      car1 = expr.charAt(i);
      car2 = expr.charAt(i+1);
      car3 = expr.charAt(i+2);
      car4 = expr.charAt(i+3);
      if(car1 >= 'a' && car1 <= 'z' && car2 >= 'a' && car2 <= 'z' && car3 >= 'a' && car3 <= 'z' && car4 >= 'a' && car4 <= 'z')
        band=true;
    }
    return band;
  }
  /**
   * No utilizado
  */
  //18.1 Si detecta tres letras seguidas y luego un par�ntesis que abre,
  // entonces verifica si es funci�n o no
  private boolean FuncionInvalida(){
    boolean band=false;
    for(int i=0;i<expr.length()-2;i++){ 
      char car1 = expr.charAt(i); 
      char car2 = expr.charAt(i+1);
      char car3 = expr.charAt(i+2);
      //Si encuentra tres letras seguidas 
      if(car1 >= 'a' && car1 <= 'z' && car2 >= 'a' && car2 <= 'z' && car3 >= 'a' && car3 <= 'z'){
        if(i<expr.length()-4){
          char car4 = expr.charAt(i+3);
          //Si el siguiente car�cter es(
          if( car4=='(' ){
            boolean isFuncion = esUnaFuncion(car1,car2,car3);
            if(isFuncion==false)
              band=true; 
          }
          else
            band=true; //Hay un error porque no hay par�ntesis 
        }
        else
          band=true; //Hay un error porque no sigue par�ntesis 
      }
    } 
    return band;
  }
  //18.2 Chequea si las tres letras enviadas son una funci�n
  private boolean esUnaFuncion(char car1, char car2, char car3){
    boolean band=false;
    String listaFunciones = "sinsencostanabslogexpsqrrcb";
    for(int i = 0;i<listaFunciones.length()-2;i+=3){
      char listfunc1 = listaFunciones.charAt(i);
      char listfunc2 = listaFunciones.charAt(i+1);
      char listfunc3 = listaFunciones.charAt(i+2);
      if(car1 == listfunc1 && car2 == listfunc2 && car3 == listfunc3)
        band=true; 
    }
    return band; 
  }
  //19. Si detecta s�lo dos letras seguidas es un error
  private boolean variableInvalida(){
    boolean band=false;
    int cuentaLetras = 0;
    for(int i=0;i<expr.length();i++){
      char car = expr.charAt(i);
      if(car >= 'a' && car <= 'z'){
        char car1 = expr.charAt(i+1);
        char car2 = expr.charAt(i+2);
        if(car1 >= 'a' && car1 <= 'z')
          cuentaLetras++;
        if(car2 >= 'a' && car2 <= 'z')
          cuentaLetras++;
        //cuentaLetras++;
      }
      /*else if(cuentaLetras == 2){
        band=true;
        cuentaLetras = 0;
      }*/
      System.out.println(cuentaLetras);
    }
    if(cuentaLetras == 2)
      band=true;
    return band;
  }
  //20. Antes de par�ntesis que abre hay una letra 
  private boolean variableParentesisAbre(){
    boolean band=false;
    int cuentaLetras = 0; 
    for(int i=0;i<expr.length()-1;i++){
      char car = expr.charAt(i);
      if (car >= 'a' && car <= 'z')
        cuentaLetras++;
      else if(car == '(') 
        if(cuentaLetras == 1) 
          band=true;
        else
          cuentaLetras = 0;
    }
    return band; 
  }
  /**fin no utilizado*/
}