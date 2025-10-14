package io.srnagar.monitor.logs;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.monitor.query.LogsQueryClient;
import com.azure.monitor.query.LogsQueryClientBuilder;
import com.azure.monitor.query.models.LogsQueryResult;

import com.azure.monitor.query.models.QueryTimeInterval;

import java.time.Duration;

/**
 * Standalone Azure Monitor Logs Query sample application
 * 
 * This sample demonstrates:
 * - Querying application traces
 * - Querying performance counters
 * - Executing custom KQL queries
 * - Processing query results
 * 
 * To run this sample:
 * 1. Set the AZURE_LOG_ANALYTICS_WORKSPACE_ID environment variable
 * 2. Ensure you have proper Azure credentials configured (Azure CLI, Managed Identity, etc.)
 * 3. Run: java io.srnagar.monitor.logs.App
 */
public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Azure Monitor Logs Query Sample ===");
        
        // Get workspace ID from environment variable
        String workspaceId = System.getenv("AZURE_LOG_ANALYTICS_WORKSPACE_ID");
        if (workspaceId == null) {
            workspaceId = "your-workspace-id-here";
            System.out.println("ℹ️  AZURE_LOG_ANALYTICS_WORKSPACE_ID not set, using placeholder");
            System.out.println("   Set environment variable AZURE_LOG_ANALYTICS_WORKSPACE_ID to your Log Analytics workspace ID");
        }
        
        try {
            // Create LogsQueryClient using DefaultAzureCredential
            LogsQueryClient logsQueryClient = new LogsQueryClientBuilder()
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();
            
            System.out.println("\\n📊 Executing sample queries...");
            
            // Query 1: Application traces from the last hour
            System.out.println("\\n🔍 Query 1: Application Traces (last hour)");
            queryApplicationTraces(logsQueryClient, workspaceId);
            
            // Query 2: Heartbeat data
            System.out.println("\\n💓 Query 2: Heartbeat Data (last 30 minutes)");
            queryHeartbeat(logsQueryClient, workspaceId);
            
            // Query 3: Performance counters
            System.out.println("\\n⚡ Query 3: Performance Counters (last hour)");
            queryPerformanceCounters(logsQueryClient, workspaceId);
            
            System.out.println("\\n🎉 Azure Monitor Logs sample completed successfully!");
            
        } catch (Exception e) {
            System.err.println("❌ Error running Azure Monitor Logs sample:");
            System.err.println("   " + e.getClass().getSimpleName() + ": " + e.getMessage());
            System.err.println();
            System.err.println("💡 Make sure to:");
            System.err.println("   1. Set AZURE_LOG_ANALYTICS_WORKSPACE_ID environment variable");
            System.err.println("   2. Configure Azure authentication (az login, managed identity, etc.)");
            System.err.println("   3. Ensure your account has Log Analytics Reader role");
        }
    }
    
    private static void queryApplicationTraces(LogsQueryClient client, String workspaceId) {
        try {
            String kustoQuery = "AppTraces | where TimeGenerated > ago(1h) | limit 10";
            
            QueryTimeInterval timeInterval = new QueryTimeInterval(Duration.ofHours(1));
            LogsQueryResult result = client.queryWorkspace(workspaceId, kustoQuery, timeInterval);
            
            System.out.println("   Query executed successfully!");
            System.out.println("   Tables returned: " + result.getAllTables().size());
            
            if (!result.getAllTables().isEmpty()) {
                var table = result.getAllTables().get(0);
                System.out.println("   Rows returned: " + table.getRows().size());
                
                table.getRows().stream().limit(3).forEach(row -> {
                    System.out.println("     Row: " + row.toString());
                });
            }
        } catch (Exception e) {
            System.out.println("   ⚠️  Could not execute query (workspace may not have AppTraces data): " + e.getMessage());
        }
    }
    
    private static void queryHeartbeat(LogsQueryClient client, String workspaceId) {
        try {
            String kustoQuery = "Heartbeat | where TimeGenerated > ago(30m) | limit 5";
            
            QueryTimeInterval timeInterval = new QueryTimeInterval(Duration.ofMinutes(30));
            LogsQueryResult result = client.queryWorkspace(workspaceId, kustoQuery, timeInterval);
            
            System.out.println("   Query executed successfully!");
            System.out.println("   Tables returned: " + result.getAllTables().size());
            
            if (!result.getAllTables().isEmpty()) {
                var table = result.getAllTables().get(0);
                System.out.println("   Rows returned: " + table.getRows().size());
                
                table.getRows().stream().limit(2).forEach(row -> {
                    System.out.println("     Heartbeat row: " + row.toString());
                });
            }
        } catch (Exception e) {
            System.out.println("   ⚠️  Could not execute heartbeat query: " + e.getMessage());
        }
    }
    
    private static void queryPerformanceCounters(LogsQueryClient client, String workspaceId) {
        try {
            String kustoQuery = """
                Perf 
                | where TimeGenerated > ago(1h) 
                | where ObjectName == "Processor" 
                | where CounterName == "% Processor Time" 
                | limit 5
                """;
            
            QueryTimeInterval timeInterval = new QueryTimeInterval(Duration.ofHours(1));
            LogsQueryResult result = client.queryWorkspace(workspaceId, kustoQuery, timeInterval);
            
            System.out.println("   Query executed successfully!");
            System.out.println("   Tables returned: " + result.getAllTables().size());
            
            if (!result.getAllTables().isEmpty()) {
                var table = result.getAllTables().get(0);
                System.out.println("   Rows returned: " + table.getRows().size());
                
                table.getRows().stream().limit(2).forEach(row -> {
                    System.out.println("     Performance row: " + row.toString());
                });
            }
        } catch (Exception e) {
            System.out.println("   ⚠️  Could not execute performance query: " + e.getMessage());
        }
    }
}