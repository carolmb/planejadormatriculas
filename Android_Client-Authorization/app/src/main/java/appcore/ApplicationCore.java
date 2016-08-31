package appcore;

/**
 * Created by Luisa on 31/08/2016.
 */

import org.json.JSONObject;

import datarepresentation.*;
import externaldata.*;

public class ApplicationCore {

    private static ApplicationCore instance = new ApplicationCore();

    private ApplicationCore() {}

    public static ApplicationCore getInstance() {
        return instance;
    }

    public void getStudent(final DataReceiver receiver) {
        // TODO: verificar se tá logado, e se tiver retorna o usuário atual
        // TODO: se não, abre a página de login usando o externaldata
        ActionRequest action = new ActionRequest() {
            public void run(String response) {
                Student student = null; // TODO: converter a response para JSON e pegar o dados pra criar o student
                receiver.onReceive(student);
            }
        };
        // Chamar aqui o external data usando o action acima
    }

}
