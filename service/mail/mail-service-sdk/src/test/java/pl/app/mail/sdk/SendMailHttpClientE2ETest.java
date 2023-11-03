package pl.app.mail.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class SendMailHttpClientE2ETest {
    private final String sendMailServiceURI = "http://localhost:9005/api/v1";

    private final String applicationId = "testApplicationId";

    private final SendMailHttpClient sendMailHttpClient = new SendMailHttpClient(new ObjectMapper(), HttpClient.newBuilder().build(), sendMailServiceURI, applicationId);

    @Test
    void whenValidCommand_thenRespondShouldBe202() {
        // given
        SendMailCommand command = SendMailCommand.builder()
                .to("dawidbladek0831@gmail.com")
                .subject("testSubject")
                .templateName("testTemplateName")
                .properties(List.of(new SendMailCommand.Property("link", "localhost")))
                .build();
        // when
        HttpResponse<Void> voidHttpResponse = sendMailHttpClient.sendMail(command);
        // then
        assertThat(voidHttpResponse.statusCode()).isEqualTo(202);
    }

    @Test
    void whenValidCommand_thenRespondShouldBe202Async() throws ExecutionException, InterruptedException {
        // given
        SendMailCommand command = SendMailCommand.builder()
                .to("dawidbladek0831@gmail.com")
                .subject("testSubject")
                .templateName("testTemplateName")
                .properties(List.of(new SendMailCommand.Property("link", "localhost")))
                .build();
        // when
        CompletableFuture<HttpResponse<Void>> httpResponseCompletableFuture = sendMailHttpClient.sendMailAsync(command);
        // then
        HttpResponse<Void> voidHttpResponse = httpResponseCompletableFuture.get();
        assertThat(voidHttpResponse.statusCode()).isEqualTo(202);
    }
}