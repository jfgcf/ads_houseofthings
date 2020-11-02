package service;

public class SlackMessage {

    public static String Slackmessageconnect(String message){

        String messagetotal;

        messagetotal="{ \"text\" :\""+message+"\"}";

        //para testes
        //messagetotal="{\"text\" : \"teste\"}";

        return messagetotal;

    }

}
