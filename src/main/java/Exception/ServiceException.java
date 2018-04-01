package Exception;

public class ServiceException extends Exception{
	 public ServiceException(){ }
     public ServiceException(String cadena){
                super(cadena); //Llama al constructor de Exception y le pasa el contenido de cadena
     }

}
