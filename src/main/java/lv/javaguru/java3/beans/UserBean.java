package lv.javaguru.java3.beans;

        import com.fasterxml.jackson.databind.ObjectMapper;
        import com.fasterxml.jackson.databind.type.TypeFactory;
        import lv.javaguru.java3.core.dto.idea.IdeaDTO;
        import lv.javaguru.java3.core.dto.user.UserDTO;
        import org.apache.http.HttpEntity;
        import org.apache.http.HttpResponse;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.methods.HttpGet;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.json.XML;

        import javax.faces.bean.ManagedBean;
        import javax.faces.bean.ManagedProperty;
        import javax.faces.bean.RequestScoped;
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.util.ArrayList;
        import java.util.HashSet;
        import java.util.List;
        import java.util.Set;

/**
 * Created by Anna on 23.01.2016.
 */
@ManagedBean
@RequestScoped
public class UserBean{

    private static String userLink = new String("http://localhost:8080/rest/users/get/");
    private static String userIdeasLink = new String("http://localhost:8080/rest/users/get_ideas/");
    //private static String ideaLink = new String("http://localhost:8080/rest/ideas/get/");


    @ManagedProperty(value="#{param.id}")
    public Long id;

    public UserDTO userDTO;

    public List<IdeaDTO> userIdeas = new ArrayList<IdeaDTO>();

    public List<String> userIdeasTitles = new ArrayList<String>();

    public List<IdeaDTO> getUserIdeas() {
        return userIdeas;
    }

    public void setUserIdeas(List<IdeaDTO> userIdeas) {
        this.userIdeas = userIdeas;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public UserDTO getUserById(Long id) {

        String link = userLink + String.valueOf(id);

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(link);
        HttpResponse response;
        String result = null;
        userDTO = null;
        try {
            response = client.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {

                // A Simple JSON Response Read
                InputStream instream = entity.getContent();
                result = convertStreamToString(instream);
                System.out.println("RESPONSE result: " + result);

                org.json.JSONObject xmlJSONObj = XML.toJSONObject(result);
                System.out.println("RESPONSE json: " + xmlJSONObj);
                //userDTO = xmlJSONObj;

                ObjectMapper mapper = new ObjectMapper();
                String jsonString = xmlJSONObj.toString();
                jsonString = jsonString.substring(jsonString.indexOf(":")+1, jsonString.length()-1);
                System.out.println("RESPONSE json string: " + jsonString);

                userDTO = mapper.readValue(jsonString, UserDTO.class);

                instream.close();
            }
            // Headers
            org.apache.http.Header[] headers = response.getAllHeaders();
            for (int i = 0; i < headers.length; i++) {
                System.out.println(headers[i]);
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        getUserIdeas(userIdeasLink + String.valueOf(id));
        return userDTO;
    }

    public void getUserIdeas(String link) {

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(link);
        HttpResponse response;
        String result = null;
        try {
            response = client.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {

                // A Simple JSON Response Read
                InputStream instream = entity.getContent();
                result = convertStreamToString(instream);
                System.out.println("RESPONSE result: " + result);

                org.json.JSONObject xmlJSONObj = XML.toJSONObject(result);
                System.out.println("RESPONSE json: " + xmlJSONObj);

                ObjectMapper mapper = new ObjectMapper();
                String jsonString = xmlJSONObj.toString();

                if(jsonString.indexOf("[") > 0){
                    jsonString = jsonString.substring(jsonString.indexOf("["), jsonString.length()-2);
                    System.out.println("RESPONSE json string: " + jsonString);
                } else {
                    jsonString = jsonString.substring(jsonString.indexOf(":")+2, jsonString.length()-2);
                    jsonString = "[" + jsonString.substring(jsonString.indexOf(":")+1, jsonString.length()) + "]";
                    System.out.println("RESPONSE json string: " + jsonString);
                }


                Set<IdeaDTO> userIdeasSET = new HashSet<IdeaDTO>();
                userIdeasSET = mapper.readValue(jsonString,
                        TypeFactory.defaultInstance().constructCollectionType(Set.class,
                                IdeaDTO.class));

                if(userIdeasSET.size() > 0){
                    for(IdeaDTO i : userIdeasSET){
                        userIdeas.add(i);
                    }
                }


                instream.close();
            }
            // Headers
            org.apache.http.Header[] headers = response.getAllHeaders();
            for (int i = 0; i < headers.length; i++) {
                System.out.println(headers[i]);
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public List<String> getUserIdeasTitles(){
        List<String> ideasTitles = new ArrayList<String>();
        List<IdeaDTO> ideasDTO = getUserIdeas();
        if(getUserIdeas() != null && ideasDTO.size() > 0 ){
            for(IdeaDTO i : ideasDTO){
                if(!ideasTitles.contains(i.getTitle())){
                    ideasTitles.add(i.getTitle());
                }
            }
        }
        return ideasTitles;
    }

}