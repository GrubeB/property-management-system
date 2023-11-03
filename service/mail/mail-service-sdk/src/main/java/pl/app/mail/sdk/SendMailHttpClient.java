package pl.app.mail.sdk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class SendMailHttpClient {
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private final String sendMailServiceBaseURL;
    private final String applicationId;

    public HttpResponse<Void> sendMail(SendMailCommand command) {
        try {
            HttpRequest request = buildHttpRequestForCommand(command);
            return httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid SendMailHttpClient base URL:" + sendMailServiceBaseURL, e);
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread was interrupted while sending mail to :" + command.getTo(), e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public CompletableFuture<HttpResponse<Void>> sendMailAsync(SendMailCommand command) {
        try {
            HttpRequest request = buildHttpRequestForCommand(command);
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid SendMailHttpClient base URL:" + sendMailServiceBaseURL, e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private HttpRequest buildHttpRequestForCommand(SendMailCommand command) throws JsonProcessingException, URISyntaxException {
        command.setApplicationId(applicationId);
        String commandString = objectMapper.writeValueAsString(command);
        return HttpRequest.newBuilder(new URI(sendMailServiceBaseURL + "/mail/send-mail"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(commandString))
                .build();
    }

}
