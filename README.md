# Azure SDK Samples

This repository contains independent Azure SDK samples for Java, demonstrating various Azure services and their usage patterns.

## 📦 Available Samples

### 1. 🗄️ Azure Storage Blob Sample
**Location:** `io.srnagar.storage.App`

Demonstrates Azure Blob Storage operations including:
- Creating containers
- Uploading and downloading blobs
- Listing blobs
- Container cleanup

**Environment Variables:**
- `AZURE_STORAGE_ACCOUNT_URL` - Your Azure Storage account URL

**Run:**
```bash
mvn exec:java -Dexec.mainClass="io.srnagar.storage.App"
```

### 2. 📊 Azure Monitor Logs Sample
**Location:** `io.srnagar.monitor.logs.App`

Demonstrates Azure Monitor Log Analytics queries including:
- Application traces queries
- Heartbeat monitoring
- Performance counter queries
- Custom KQL queries

**Environment Variables:**
- `AZURE_LOG_ANALYTICS_WORKSPACE_ID` - Your Log Analytics workspace ID

**Run:**
```bash
mvn exec:java -Dexec.mainClass="io.srnagar.monitor.logs.App"
```

### 3. 📈 Azure Monitor Metrics Sample
**Location:** `io.srnagar.monitor.metrics.App`

Demonstrates Azure Monitor Metrics queries including:
- CPU metrics
- Memory metrics
- Network metrics
- Custom metrics queries

**Environment Variables:**
- `AZURE_RESOURCE_URI` - Azure resource URI (e.g., VM, App Service)

**Run:**
```bash
mvn exec:java -Dexec.mainClass="io.srnagar.monitor.metrics.App"
```

### 4. 🤖 Azure OpenAI Sample
**Location:** `io.srnagar.openai.App`

Demonstrates Azure OpenAI operations including:
- Text completions
- Chat completions
- System message conversations
- Authentication patterns

**Environment Variables:**
- `AZURE_OPENAI_ENDPOINT` - Your Azure OpenAI endpoint
- `AZURE_OPENAI_API_KEY` - Your Azure OpenAI API key (optional with DefaultAzureCredential)
- `AZURE_OPENAI_DEPLOYMENT_NAME` - Your model deployment name

**Run:**
```bash
mvn exec:java -Dexec.mainClass="io.srnagar.openai.App"
```

## 🚀 Quick Start

### Prerequisites
- **Java 17** or later
- **Maven 3.6+**
- **Azure CLI** (for authentication)
- **Azure subscription** with appropriate resources

### Setup
1. **Clone the repository:**
   ```bash
   git clone https://github.com/srnagar/azure-sdk-samples.git
   cd azure-sdk-samples
   ```

2. **Build the project:**
   ```bash
   mvn clean compile
   ```

3. **Configure Azure authentication:**
   ```bash
   az login
   ```

4. **Set environment variables** for the samples you want to run (see individual sample requirements above)

5. **Run a sample:**
   ```bash
   mvn exec:java -Dexec.mainClass="io.srnagar.storage.App"
   ```

## 🔧 Configuration

### Authentication
All samples use **DefaultAzureCredential** which supports multiple authentication methods:
- Azure CLI (`az login`)
- Managed Identity (when running in Azure)
- Environment variables (`AZURE_CLIENT_ID`, `AZURE_CLIENT_SECRET`, `AZURE_TENANT_ID`)
- Visual Studio Code
- IntelliJ IDEA

Some samples also support API key authentication as an alternative.

### Environment Variables
Each sample requires specific environment variables. Set them in your shell or create a `.env` file:

```bash
# Storage Sample
export AZURE_STORAGE_ACCOUNT_URL="https://yourstorageaccount.blob.core.windows.net"

# Monitor Logs Sample  
export AZURE_LOG_ANALYTICS_WORKSPACE_ID="your-workspace-id"

# Monitor Metrics Sample
export AZURE_RESOURCE_URI="/subscriptions/sub-id/resourceGroups/rg-name/providers/Microsoft.Compute/virtualMachines/vm-name"

# OpenAI Sample
export AZURE_OPENAI_ENDPOINT="https://your-openai-resource.openai.azure.com"
export AZURE_OPENAI_API_KEY="your-api-key"
export AZURE_OPENAI_DEPLOYMENT_NAME="gpt-35-turbo"
```

## 📋 Dependencies

The project uses the latest Azure SDK for Java libraries:

- **Azure Identity:** `com.azure:azure-identity`
- **Azure Storage Blob:** `com.azure:azure-storage-blob`  
- **Azure Monitor Query:** `com.azure:azure-monitor-query`
- **Azure AI OpenAI:** `com.azure:azure-ai-openai`
- **OpenAI Java:** `com.openai:openai-java` (latest version)

## 🏗️ Project Structure

```
src/main/java/io/srnagar/
├── App.java                    # Main overview and instructions
├── storage/
│   └── App.java               # Azure Storage Blob sample
├── monitor/
│   ├── logs/
│   │   └── App.java          # Azure Monitor Logs sample  
│   └── metrics/
│       └── App.java          # Azure Monitor Metrics sample
└── openai/
    └── App.java              # Azure OpenAI sample
```

## ⚠️ Important Notes

- **Costs:** Running these samples may incur Azure charges
- **Permissions:** Ensure your account has appropriate RBAC roles
- **Resources:** Some samples require existing Azure resources
- **Cleanup:** Samples include cleanup code where applicable

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📚 Additional Resources

- [Azure SDK for Java Documentation](https://docs.microsoft.com/en-us/java/api/overview/azure/)
- [Azure Identity Documentation](https://docs.microsoft.com/en-us/java/api/overview/azure/identity-readme)
- [Azure Storage Documentation](https://docs.microsoft.com/en-us/azure/storage/)
- [Azure Monitor Documentation](https://docs.microsoft.com/en-us/azure/azure-monitor/)
- [Azure OpenAI Documentation](https://docs.microsoft.com/en-us/azure/cognitive-services/openai/)