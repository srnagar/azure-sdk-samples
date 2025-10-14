package io.srnagar;

/**
 * Azure SDK Samples - Main Application
 * 
 * This project contains independent Azure SDK samples in different packages.
 * Each package contains a standalone App.java that can be run independently.
 * 
 * Available Samples:
 * 
 * 1. Azure Storage Blob Sample
 *    Package: io.srnagar.storage
 *    Run: java io.srnagar.storage.App
 *    Requirements: AZURE_STORAGE_ACCOUNT_URL environment variable
 * 
 * 2. Azure Monitor Logs Query Sample  
 *    Package: io.srnagar.monitor.logs
 *    Run: java io.srnagar.monitor.logs.App
 *    Requirements: AZURE_LOG_ANALYTICS_WORKSPACE_ID environment variable
 * 
 * 3. Azure Monitor Metrics Query Sample
 *    Package: io.srnagar.monitor.metrics  
 *    Run: java io.srnagar.monitor.metrics.App
 *    Requirements: AZURE_RESOURCE_URI environment variable
 * 
 * 4. Azure OpenAI Sample
 *    Package: io.srnagar.openai
 *    Run: java io.srnagar.openai.App
 *    Requirements: AZURE_OPENAI_ENDPOINT, AZURE_OPENAI_API_KEY, AZURE_OPENAI_DEPLOYMENT_NAME
 * 
 * Prerequisites:
 * - Java 17 or later
 * - Azure authentication configured (Azure CLI login, managed identity, etc.)
 * - Appropriate Azure resources and permissions
 */
public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Azure SDK Samples Project ===\\n");
        
        System.out.println("This project contains independent Azure SDK samples.");
        System.out.println("Each sample is in its own package and can be run independently.\\n");
        
        System.out.println("📦 Available Samples:");
        System.out.println();
        
        System.out.println("1. 🗄️  Azure Storage Blob Sample");
        System.out.println("   Location: io.srnagar.storage.App");
        System.out.println("   Run: mvn exec:java -Dexec.mainClass=\\\"io.srnagar.storage.App\\\"");
        System.out.println("   Environment: AZURE_STORAGE_ACCOUNT_URL");
        System.out.println();
        
        System.out.println("2. 📊 Azure Monitor Logs Sample");
        System.out.println("   Location: io.srnagar.monitor.logs.App");
        System.out.println("   Run: mvn exec:java -Dexec.mainClass=\\\"io.srnagar.monitor.logs.App\\\"");
        System.out.println("   Environment: AZURE_LOG_ANALYTICS_WORKSPACE_ID");
        System.out.println();
        
        System.out.println("3. 📈 Azure Monitor Metrics Sample");
        System.out.println("   Location: io.srnagar.monitor.metrics.App");
        System.out.println("   Run: mvn exec:java -Dexec.mainClass=\\\"io.srnagar.monitor.metrics.App\\\"");
        System.out.println("   Environment: AZURE_RESOURCE_URI");
        System.out.println();
        
        System.out.println("4. 🤖 Azure OpenAI Sample");
        System.out.println("   Location: io.srnagar.openai.App");
        System.out.println("   Run: mvn exec:java -Dexec.mainClass=\\\"io.srnagar.openai.App\\\"");
        System.out.println("   Environment: AZURE_OPENAI_ENDPOINT, AZURE_OPENAI_API_KEY, AZURE_OPENAI_DEPLOYMENT_NAME");
        System.out.println();
        
        System.out.println("🔧 Prerequisites:");
        System.out.println("   - Configure Azure authentication (az login, managed identity, etc.)");
        System.out.println("   - Set required environment variables for each sample");
        System.out.println("   - Ensure proper Azure permissions and resources exist");
        System.out.println();
        
        System.out.println("💡 Tip: You can also run samples individually using:");
        System.out.println("   java -cp target/classes io.srnagar.<package>.App");
        System.out.println();
        
        System.out.println("For more details, check the documentation in each App.java file.");
    }
}
