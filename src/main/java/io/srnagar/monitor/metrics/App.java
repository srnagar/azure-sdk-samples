package io.srnagar.monitor.metrics;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.monitor.query.metrics.MetricsClient;
import com.azure.monitor.query.metrics.MetricsClientBuilder;
import com.azure.monitor.query.metrics.models.MetricsQueryResult;
import com.azure.monitor.query.metrics.models.MetricsQueryResourcesResult;
import com.azure.monitor.query.metrics.models.MetricValue;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;

/**
 * Standalone Azure Monitor Metrics Query sample application
 * 
 * This sample demonstrates:
 * - Querying CPU metrics
 * - Querying memory metrics
 * - Querying storage metrics
 * - Processing metric values and time series data
 * 
 * To run this sample:
 * 1. Set the AZURE_RESOURCE_URI environment variable to your Azure resource URI
 * 2. Ensure you have proper Azure credentials configured (Azure CLI, Managed Identity, etc.)
 * 3. Run: java io.srnagar.monitor.metrics.App
 */
public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Azure Monitor Metrics Query Sample ===");
        
        // Get resource URI from environment variable
        String resourceUri = System.getenv("AZURE_RESOURCE_URI");
        if (resourceUri == null) {
            resourceUri = "/subscriptions/12345678-1234-1234-1234-123456789012/resourceGroups/myResourceGroup/providers/Microsoft.Compute/virtualMachines/myVM";
            System.out.println("ℹ️  AZURE_RESOURCE_URI not set, using placeholder");
            System.out.println("   Set environment variable AZURE_RESOURCE_URI to your Azure resource URI");
            System.out.println("   Example: /subscriptions/{subscription-id}/resourceGroups/{rg}/providers/Microsoft.Compute/virtualMachines/{vm-name}");
        }
        
        try {
            // Create MetricsClient using DefaultAzureCredential
            MetricsClient metricsClient = new MetricsClientBuilder()
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();
            
            System.out.println("\\n📈 Executing sample metric queries...");
            
            // Query 1: CPU metrics
            System.out.println("\\n🖥️  Query 1: CPU Metrics");
            queryCpuMetrics(metricsClient, resourceUri);
            
            // Query 2: Memory metrics (for VMs that support it)
            System.out.println("\\n🧠 Query 2: Memory Metrics");
            queryMemoryMetrics(metricsClient, resourceUri);
            
            // Query 3: Network metrics
            System.out.println("\\n🌐 Query 3: Network Metrics");
            queryNetworkMetrics(metricsClient, resourceUri);
            
            System.out.println("\\n🎉 Azure Monitor Metrics sample completed successfully!");
            
        } catch (Exception e) {
            System.err.println("❌ Error running Azure Monitor Metrics sample:");
            System.err.println("   " + e.getClass().getSimpleName() + ": " + e.getMessage());
            System.err.println();
            System.err.println("💡 Make sure to:");
            System.err.println("   1. Set AZURE_RESOURCE_URI environment variable to a valid Azure resource");
            System.err.println("   2. Configure Azure authentication (az login, managed identity, etc.)");
            System.err.println("   3. Ensure your account has Monitoring Reader role");
            System.err.println("   4. The specified resource exists and supports the requested metrics");
        }
    }
    
    private static void queryCpuMetrics(MetricsClient client, String resourceUri) {
        try {
            MetricsQueryResourcesResult result = client.queryResources(
                Collections.singletonList(resourceUri),
                Arrays.asList("Percentage CPU"),
                "Microsoft.Compute/virtualMachines"
            );
            
            System.out.println("   CPU metrics query executed successfully!");
            System.out.println("   Resource queries returned: " + result.getMetricsQueryResults().size());
            
            for (MetricsQueryResult queryResult : result.getMetricsQueryResults()) {
                System.out.println("   Resource ID: " + queryResult.getResourceId());
                queryResult.getMetrics().forEach(metric -> {
                    System.out.println("   Metric: " + metric.getMetricName() + " (Unit: " + metric.getUnit() + ")");
                    
                    metric.getTimeSeries().stream().limit(1).forEach(timeSeries -> {
                        System.out.println("     Time series with " + timeSeries.getValues().size() + " values");
                        timeSeries.getValues().stream().limit(3).forEach(value -> {
                            printMetricValue("CPU", value);
                        });
                    });
                });
            }
            
        } catch (Exception e) {
            System.out.println("   ⚠️  Could not query CPU metrics: " + e.getMessage());
        }
    }
    
    private static void queryMemoryMetrics(MetricsClient client, String resourceUri) {
        try {
            MetricsQueryResourcesResult result = client.queryResources(
                Collections.singletonList(resourceUri),
                Arrays.asList("Available Memory Bytes"),
                "Microsoft.Compute/virtualMachines"
            );
            
            System.out.println("   Memory metrics query executed successfully!");
            System.out.println("   Resource queries returned: " + result.getMetricsQueryResults().size());
            
            for (MetricsQueryResult queryResult : result.getMetricsQueryResults()) {
                System.out.println("   Resource ID: " + queryResult.getResourceId());
                queryResult.getMetrics().forEach(metric -> {
                    System.out.println("   Metric: " + metric.getMetricName() + " (Unit: " + metric.getUnit() + ")");
                    
                    metric.getTimeSeries().stream().limit(1).forEach(timeSeries -> {
                        System.out.println("     Time series with " + timeSeries.getValues().size() + " values");
                        timeSeries.getValues().stream().limit(2).forEach(value -> {
                            printMetricValue("Memory", value);
                        });
                    });
                });
            }
            
        } catch (Exception e) {
            System.out.println("   ⚠️  Could not query memory metrics (may not be available for this resource): " + e.getMessage());
        }
    }
    
    private static void queryNetworkMetrics(MetricsClient client, String resourceUri) {
        try {
            MetricsQueryResourcesResult result = client.queryResources(
                Collections.singletonList(resourceUri),
                Arrays.asList("Network In Total", "Network Out Total"),
                "Microsoft.Compute/virtualMachines"
            );
            
            System.out.println("   Network metrics query executed successfully!");
            System.out.println("   Resource queries returned: " + result.getMetricsQueryResults().size());
            
            for (MetricsQueryResult queryResult : result.getMetricsQueryResults()) {
                System.out.println("   Resource ID: " + queryResult.getResourceId());
                queryResult.getMetrics().forEach(metric -> {
                    System.out.println("   Metric: " + metric.getMetricName() + " (Unit: " + metric.getUnit() + ")");
                    
                    metric.getTimeSeries().stream().limit(1).forEach(timeSeries -> {
                        System.out.println("     Time series with " + timeSeries.getValues().size() + " values");
                        timeSeries.getValues().stream().limit(2).forEach(value -> {
                            printMetricValue("Network", value);
                        });
                    });
                });
            }
            
        } catch (Exception e) {
            System.out.println("   ⚠️  Could not query network metrics: " + e.getMessage());
        }
    }
    
    private static void printMetricValue(String metricType, MetricValue value) {
        OffsetDateTime timestamp = value.getTimeStamp();
        Double average = value.getAverage();
        Double maximum = value.getMaximum();
        Double minimum = value.getMinimum();
        Double total = value.getTotal();
        
        System.out.printf("       %s [%s] Avg: %.2f, Max: %.2f, Min: %.2f, Total: %.2f%n", 
            metricType,
            timestamp != null ? timestamp.toString() : "Unknown", 
            average != null ? average : 0.0, 
            maximum != null ? maximum : 0.0, 
            minimum != null ? minimum : 0.0,
            total != null ? total : 0.0
        );
    }
}