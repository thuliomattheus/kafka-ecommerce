rootProject.name = "kafka-ecommerce"
include("src:main:java")
findProject(":src:main:java")?.name = "java"
