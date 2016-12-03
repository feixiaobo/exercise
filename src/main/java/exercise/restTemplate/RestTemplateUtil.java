package exercise.restTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by feixiaobo on 2016/11/7.
 */
public class RestTemplateUtil {
    @Autowired RestTemplate restTemplate;


    public <T> T restForObject(String url, Map<String,T> params, Class<T> classType, HttpMethod method) {
        MultiValueMap<String, T> map = new LinkedMultiValueMap<>();
        map.setAll(params);
        switch (method) {
            case POST:
                return restTemplate.postForObject(url, map, classType);
            case GET:
                String getParams = "?" + map.keySet().stream().map(k -> String.format("%s={%s}", k, k)).collect(
                    Collectors.joining("&"));
            default:
                return restTemplate.postForObject(url, map, classType);
        }
    }

    public <T> void downloadFile(String url, Map<String,T> params, Class<T> classType, HttpMethod method , String PDFPath) throws
        IOException {
        MultiValueMap<String, T> map = new LinkedMultiValueMap<>();
        map.setAll(params);
        String getParams = "?" + map.keySet().stream().map(k -> String.format("%s={%s}", k, k)).collect(
            Collectors.joining("&"));
        final String APPLICATION_PDF = "application/pdf";
        HttpHeaders headers = new HttpHeaders();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            headers.setAccept(Arrays.asList(MediaType.valueOf(APPLICATION_PDF)));

            ResponseEntity<byte[]> response = restTemplate.exchange(
                url+getParams,
                //HttpMethod.GET,
                method,
                new HttpEntity<byte[]>(headers),
                byte[].class);

            byte[] result = response.getBody();

            inputStream = new ByteArrayInputStream(result);

            File file = new File(PDFPath);
            if (!file.exists())
            {
                file.createNewFile();
            }

            outputStream = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();

        }finally {
            if(inputStream != null){
                inputStream.close();
            }
            if(outputStream != null){
                outputStream.close();
            }
        }
    }
}
