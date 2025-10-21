package io.srnagar.openai;

import com.azure.identity.AuthenticationUtil;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.credential.BearerTokenCredential;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.embeddings.CreateEmbeddingResponse;
import com.openai.models.embeddings.EmbeddingCreateParams;
import com.openai.models.embeddings.EmbeddingModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;

import java.util.function.Supplier;

public class AzureOpenAISample {

    public static void main(String[] args) {
        DefaultAzureCredential tokenCredential = new DefaultAzureCredentialBuilder().build();
        Supplier<String> bearerTokenSupplier = AuthenticationUtil.getBearerTokenSupplier(
                tokenCredential, "https://cognitiveservices.azure.com/.default");
        OpenAIClient client = OpenAIOkHttpClient.builder()
                .fromEnv()
                // Set the Azure Entra ID
                .credential(BearerTokenCredential.create(bearerTokenSupplier))
                .build();

        ResponseCreateParams responseCreateParams = ResponseCreateParams.builder()
                .input("This is a test")
                .model(ChatModel.GPT_4_1_NANO)
                .build();

        Response response = client.responses().create(responseCreateParams);

        ChatCompletionCreateParams chatCompletionCreateParams = ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_4O)
                .addUserMessage("<message>")
                .build();

        ChatCompletion chatCompletion = client.chat().completions().create(chatCompletionCreateParams);

        EmbeddingCreateParams embeddingCreateParams = EmbeddingCreateParams.builder()
                .input("<input>")
                .model(EmbeddingModel.TEXT_EMBEDDING_3_LARGE)
                .build();

        CreateEmbeddingResponse createEmbeddingResponse = client.embeddings().create(embeddingCreateParams);
    }
}
