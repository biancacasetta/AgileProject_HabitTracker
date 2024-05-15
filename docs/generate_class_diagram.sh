#!/bin/bash

# Path to the PlantUML jar file in the lib folder
PLANTUML_JAR="./lib/plantuml.jar"

# Create docs folder if it doesn't exist
mkdir -p docs

# Start the PlantUML file
echo "@startuml" > docs/diagram.puml

# Add classes and relationships from Java files
find src -name "*.java" | while read -r file; do
    class_name=$(grep -E 'public class|public interface' "$file" | awk '{print $3}')
    if [ ! -z "$class_name" ]; then
        echo "class $class_name {" >> docs/diagram.puml
        grep -E 'public|protected|private' "$file" | grep -vE 'class|interface' | awk '{$1=$1};1' | while read -r line; do
            echo "    $line" >> docs/diagram.puml
        done
        echo "}" >> docs/diagram.puml

        # Detect inheritance (extends) and implementation (implements) relationships
        super_class=$(grep -E 'public class' "$file" | grep -o 'extends [a-zA-Z0-9_]*' | awk '{print $2}')
        if [ ! -z "$super_class" ]; then
            echo "$super_class <|-- $class_name" >> docs/diagram.puml
        fi
        interfaces=$(grep -E 'public class|public interface' "$file" | grep -o 'implements [a-zA-Z0-9_, ]*' | awk '{print $2}' | tr ',' ' ')
        for interface in $interfaces; do
            echo "$interface <|.. $class_name" >> docs/diagram.puml
        done

        # Detect associations (fields referencing other classes)
        grep -E 'private|protected|public' "$file" | grep -o '[a-zA-Z0-9_]* [a-zA-Z0-9_]*;' | while read -r line; do
            field_type=$(echo $line | awk '{print $1}')
            if grep -qw "$field_type" <<< "$(find src -name "*.java" -exec grep -E 'public class|public interface' {} \; | awk '{print $3}')"; then
                echo "$class_name --> $field_type : has-a" >> docs/diagram.puml
            fi
        done
    fi
done

# End the PlantUML file
echo "@enduml" >> docs/diagram.puml

# Generate the class diagram image
java -jar $PLANTUML_JAR docs/diagram.puml -o docs/

