curl -X POST -H "Content-Type: application/json" --data '{ "name": "orders-file-sink", "config": { "connector.class" : "FileStreamSink", "tasks.max" : 1, "file" : "/kafka-connect/data/orders.txt", "topics" : "mysql-orders" } }'  http://localhost:8083/connectors