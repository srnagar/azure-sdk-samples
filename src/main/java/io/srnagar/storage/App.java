package io.srnagar.storage;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

import java.io.ByteArrayInputStream;

/**
 * Standalone Azure Storage Blob sample application
 * 
 * This sample demonstrates:
 * - Creating blob containers
 * - Uploading blobs
 * - Downloading blobs
 * - Listing blobs in a container
 * 
 * To run this sample:
 * 1. Set the AZURE_STORAGE_ACCOUNT_URL environment variable
 * 2. Ensure you have proper Azure credentials configured (Azure CLI, Managed Identity, etc.)
 * 3. Run: java io.srnagar.storage.App
 */
public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Azure Storage Blob Sample ===");
        
        // Get storage account URL from environment variable
        String storageAccountUrl = System.getenv("AZURE_STORAGE_ACCOUNT_URL");
        if (storageAccountUrl == null) {
            storageAccountUrl = "https://yourstorageaccount.blob.core.windows.net";
            System.out.println("ℹ️  AZURE_STORAGE_ACCOUNT_URL not set, using placeholder: " + storageAccountUrl);
            System.out.println("   Set environment variable AZURE_STORAGE_ACCOUNT_URL to your storage account URL");
        }
        
        try {
            // Create BlobServiceClient using DefaultAzureCredential
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .endpoint(storageAccountUrl)
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();
            
            String containerName = "sample-container-" + System.currentTimeMillis();
            String blobName = "sample-blob.txt";
            String content = "Hello Azure Storage! This is a sample blob content.";
            
            System.out.println("\\n📁 Creating container: " + containerName);
            
            // Create container
            BlobContainerClient containerClient = blobServiceClient.createBlobContainer(containerName);
            System.out.println("✅ Container created successfully");
            
            System.out.println("\\n⬆️  Uploading blob: " + blobName);
            
            // Upload blob
            BlobClient blobClient = containerClient.getBlobClient(blobName);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());
            blobClient.upload(inputStream, content.getBytes().length, true);
            System.out.println("✅ Blob uploaded successfully");
            
            System.out.println("\\n⬇️  Downloading blob: " + blobName);
            
            // Download blob
            String downloadedContent = blobClient.downloadContent().toString();
            System.out.println("📄 Downloaded content: " + downloadedContent);
            
            System.out.println("\\n📋 Listing blobs in container:");
            
            // List blobs
            containerClient.listBlobs().forEach(blobItem -> {
                System.out.println("  - " + blobItem.getName() + " (Size: " + blobItem.getProperties().getContentLength() + " bytes)");
            });
            
            System.out.println("\\n🗑️  Cleaning up - deleting container: " + containerName);
            
            // Clean up - delete container
            containerClient.delete();
            System.out.println("✅ Container deleted successfully");
            
            System.out.println("\\n🎉 Azure Storage Blob sample completed successfully!");
            
        } catch (Exception e) {
            System.err.println("❌ Error running Azure Storage sample:");
            System.err.println("   " + e.getClass().getSimpleName() + ": " + e.getMessage());
            System.err.println();
            System.err.println("💡 Make sure to:");
            System.err.println("   1. Set AZURE_STORAGE_ACCOUNT_URL environment variable");
            System.err.println("   2. Configure Azure authentication (az login, managed identity, etc.)");
            System.err.println("   3. Ensure your account has Storage Blob Data Contributor role");
        }
    }
}