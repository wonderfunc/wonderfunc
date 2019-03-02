# Wonderfunc

This project has been developed with [Tomás Denis Reyes Sánchez](https://github.com/toderesa97), directed by José Juan Hernández Cabrera from SIANI (Las Palmas of Gran Canaria) and supported by Jose Évora Gómez from Monentia (Las Palmas of Gran Canaria).

Wonderfunc is a distributed stream based on JAVA Stream API. The main difference is that Wonderfunc instead of been executed in a local machine, is executed in a distributed environment.

Those distributed environments could go from a Hadoop cluster until AWS Lambda. Initially the AWS Lambda based environment will be implemented. Later on other will be added.

## API

The way you use Wonderfunc is:

First instantiate a Stream passing the input list:
``` java
public class Main() {
    public static void main(String[] args) {
        Stream<Integer> stream = new Stream<>(list());
    }
    
    private static List<String> list() {
        return Arrays.asList("Hello this is a prove to check if everything went correctly".split(" "));
    }
}
```

Once instantiated, you can use as many filters and maps as you want to work with the input list:
``` java
public class Main() {
    public static void main(String[] args) {
        Stream<Integer> stream = new Stream<>(list())
                        .filter(e -> e.contains("e"))
                        .map(String::length);
    }
    
    private static List<String> list() {
        return Arrays.asList("Hello this is a prove to check if everything went correctly".split(" "));
    }
}
```

When you want to collect the results, just use the method `collectTo()` passing the output container (list, map, ...)
``` java
public class Main() {
    public static void main(String[] args) {
        
        List<Integer> output = new ArrayList<>();
    
        Pipeline pipeline = new Stream<>(list())
                        .filter(e -> e.contains("e"))
                        .map(String::length)
                        .collectTo(output);
    }
    
    private static List<String> list() {
        return Arrays.asList("Hello this is a prove to check if everything went correctly".split(" "));
    }
}
```

**Notice that `collectTo()` method return a Pipeline object. This is the model that will be deployed depending on the deployer selected later on.**

Finally, to deploy the pipeline, just use the method `deploy()` passing a Deployer. 

``` java
public class Main() {
    public static void main(String[] args) {
        
        List<Integer> output = new ArrayList<>();
    
        Pipeline pipeline = new Stream<>(list())
                        .filter(e -> e.contains("e"))
                        .map(String::length)
                        .collectTo(output);
        
        pipeline.deploy(new HadoopDeployer("CONECTION STRING")).wait();                   
    }
    
    private static List<String> list() {
        return Arrays.asList("Hello this is a prove to check if everything went correctly".split(" "));
    }
}
```

**Notice that the deployment will init the execution in the selected platform. It is possible to wait for the execution adding the `wait()` at the end or to execute it asynchronously without using the `wait` method.**

## Components

### Stream

### Pipeline

### Deployers

## Collaborators

[Tomás Denis Reyes Sánchez](https://github.com/toderesa97)

José Juan Hernández Cabrera

Jose Évora Gómez