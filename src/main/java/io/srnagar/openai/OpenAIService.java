package io.srnagar.openai;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatRequestMessage;
import com.azure.ai.openai.models.ChatRequestUserMessage;
import com.azure.ai.openai.models.ChatRequestSystemMessage;
import com.azure.ai.openai.models.Completions;
import com.azure.ai.openai.models.CompletionsOptions;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;

import java.util.Arrays;
import java.util.List;

/**
 * Standalone Azure OpenAI sample application
 * 
 * This sample demonstrates:
 * - Text completions using Azure OpenAI
 * - Chat completions with system and user messages
 * - Authentication using both API key and DefaultAzureCredential
 * 
 * To run this sample:
 * 1. Set environment variables:
 *    - AZURE_OPENAI_ENDPOINT: Your Azure OpenAI endpoint
 *    - AZURE_OPENAI_API_KEY: Your Azure OpenAI API key (optional if using DefaultAzureCredential)
 *    - AZURE_OPENAI_DEPLOYMENT_NAME: Your model deployment name
 * 2. Run: java io.srnagar.openai.App
 */
public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Azure OpenAI Sample ===");
        
        // Get configuration from environment variables
        String endpoint = System.getenv("AZURE_OPENAI_ENDPOINT");
        String apiKey = System.getenv("AZURE_OPENAI_API_KEY");
        String deploymentName = System.getenv("AZURE_OPENAI_DEPLOYMENT_NAME");
        
        if (endpoint == null) {
            endpoint = "https://your-openai-resource.openai.azure.com";
            System.out.println("ℹ️  AZURE_OPENAI_ENDPOINT not set, using placeholder");
        }
        
        if (deploymentName == null) {
            deploymentName = "gpt-35-turbo";
            System.out.println("ℹ️  AZURE_OPENAI_DEPLOYMENT_NAME not set, using default: " + deploymentName);
        }
        
        try {
            OpenAIClient client = createOpenAIClient(endpoint, apiKey);
            
            System.out.println("\\n🤖 Running Azure OpenAI demos...");
            
            // Demo 1: Text completion
            System.out.println("\\n📝 Demo 1: Text Completion");
            demonstrateTextCompletion(client, deploymentName);
            
            // Demo 2: Chat completion
            System.out.println("\\n💬 Demo 2: Chat Completion");
            demonstrateChatCompletion(client, deploymentName);
            
            // Demo 3: Chat with system message
            System.out.println("\\n🎭 Demo 3: Chat with System Message");
            demonstrateSystemChat(client, deploymentName);
            
            System.out.println("\\n🎉 Azure OpenAI sample completed successfully!");
            
        } catch (Exception e) {
            System.err.println("❌ Error running Azure OpenAI sample:");
            System.err.println("   " + e.getClass().getSimpleName() + ": " + e.getMessage());
            System.err.println();
            System.err.println("💡 Make sure to:");
            System.err.println("   1. Set AZURE_OPENAI_ENDPOINT environment variable");
            System.err.println("   2. Set AZURE_OPENAI_API_KEY or configure Azure authentication");
            System.err.println("   3. Set AZURE_OPENAI_DEPLOYMENT_NAME to your model deployment");
            System.err.println("   4. Ensure your deployment is active and accessible");
        }
    }
    
    private static OpenAIClient createOpenAIClient(String endpoint, String apiKey) {
        OpenAIClientBuilder builder = new OpenAIClientBuilder().endpoint(endpoint);
        
        if (apiKey != null && !apiKey.isEmpty()) {
            System.out.println("   Using API key authentication");
            return builder.credential(new AzureKeyCredential(apiKey)).buildClient();
        } else {
            System.out.println("   Using DefaultAzureCredential authentication");
            return builder.credential(new DefaultAzureCredentialBuilder().build()).buildClient();
        }
    }
    
    private static void demonstrateTextCompletion(OpenAIClient client, String deploymentName) {
        try {
            String prompt = "The benefits of using Azure cloud services include";
            
            CompletionsOptions options = new CompletionsOptions(Arrays.asList(prompt))
                .setMaxTokens(100)
                .setTemperature(0.7);
            
            System.out.println("   Prompt: " + prompt);
            Completions completions = client.getCompletions(deploymentName, options);
            
            completions.getChoices().forEach(choice -> {
                System.out.println("   Response: " + choice.getText().trim());
            });
            
        } catch (Exception e) {
            System.out.println("   ⚠️  Text completion failed: " + e.getMessage());
        }
    }
    
    private static void demonstrateChatCompletion(OpenAIClient client, String deploymentName) {
        try {
            List<ChatRequestMessage> messages = Arrays.asList(
                new ChatRequestUserMessage("What are the main features of Azure Storage?")
            );
            
            ChatCompletionsOptions options = new ChatCompletionsOptions(messages)
                .setMaxTokens(150)
                .setTemperature(0.8);
            
            System.out.println("   User: What are the main features of Azure Storage?");
            ChatCompletions chatCompletions = client.getChatCompletions(deploymentName, options);
            
            chatCompletions.getChoices().forEach(choice -> {
                System.out.println("   Assistant: " + choice.getMessage().getContent());
            });
            
        } catch (Exception e) {
            System.out.println("   ⚠️  Chat completion failed: " + e.getMessage());
        }
    }
    
    private static void demonstrateSystemChat(OpenAIClient client, String deploymentName) {
        try {
            List<ChatRequestMessage> messages = Arrays.asList(
                new ChatRequestSystemMessage("You are a helpful Azure expert assistant. Provide concise and accurate information about Azure services."),
                new ChatRequestUserMessage("Explain Azure Monitor in one paragraph.")
            );
            
            ChatCompletionsOptions options = new ChatCompletionsOptions(messages)
                .setMaxTokens(200)
                .setTemperature(0.7);
            
            System.out.println("   System: You are a helpful Azure expert assistant...");
            System.out.println("   User: Explain Azure Monitor in one paragraph.");
            ChatCompletions chatCompletions = client.getChatCompletions(deploymentName, options);
            
            chatCompletions.getChoices().forEach(choice -> {
                System.out.println("   Assistant: " + choice.getMessage().getContent());
            });
            
        } catch (Exception e) {
            System.out.println("   ⚠️  System chat failed: " + e.getMessage());
        }
    }
}