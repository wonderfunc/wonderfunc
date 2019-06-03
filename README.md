# Wonderfunc

Wonderfunc is an open source framework for defining functional pipelines using lambda expresions with a high expressiveness and flexibility. Wonderfunc has been developed by Víctor Ceballos Espinosa, José Juan Hernández Cabrera and José Évora Gómez.

## User guide

This user guide's purpose is guiding the Wonderfunc user to use the main use cases.

### Getting started

Once Wonderfunc have been included in your project dependencies, you will be able to use it. To start, instantiate a Stream object passing through constructor a objects list. These objects will be the ones that the source node will start transmitting through the pipeline.

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

With the previous code we are just making the data flow through the pipeline as a stream but **no processing has been applied to that data**. Before going on, it is important to explain the difference between a stream and a pipeline. A `stream` is just data going through the pipeline. A `pipeline`is a set of chained nodes including the source node and the recollector node at the end of it. On the one hand, **the source node is added automatically** when an object from Stream class is instantiated. On the other hand, **the recollector node needs to be included manually using the `collectTo` method** passing a list as a parameter to where the result of the execution will be saved.   

``` java
public class Main() {
    public static void main(String[] args) {
    
        List<Integer> outputList = new ArrayList<>();
            
        Pipeline<Integer> stream = new Stream<>(list()).collectTo(outputList);
        
    }
    
    private static List<String> list() {
        return Arrays.asList("Hello this is a prove to check if everything went correctly".split(" "));
    }
}
```

With the previous code we have done the stream to go through the pipeline until it reaches the last node (recollector node) added with the `collectTo` method. It is important noting that when the recollector node is added, a `Pipeline` object is returned instead of a Stream. Unfortunately, this code neither makes any data processing. To **filter** or **map** the stream data, it is necessary adding map nodes or filter nodes.

### Adding map and filter nodes

After creating the data stream as showing below, **it is possible to add map of filter nodes to the pipeline.**

#### Add filter node
 
To add a filter node, just use the `filter` method passing as a parameter a lambda expression that implements the functional interface Predicate.
 
``` java
public class Main() {
    public static void main(String[] args) {
        
        List<Integer> output = new ArrayList<>();
    
        Pipeline pipeline = new Stream<>(list())
                        .filter(e -> e.contains("e"))
                        .collectTo(output);
    }
    
    private static List<String> list() {
        return Arrays.asList("Hello this is a prove to check if everything went correctly".split(" "));
    }
}
```

#### Add map node

To add a map node, just use the `map method passing as parameter a lambda expression that implements the functional interface Function.

``` java
public class Main() {
    public static void main(String[] args) {
        
        List<Integer> output = new ArrayList<>();
    
        Pipeline pipeline = new Stream<>(list())
                        .map(String::length)
                        .collectTo(output);
    }
    
    private static List<String> list() {
        return Arrays.asList("Hello this is a prove to check if everything went correctly".split(" "));
    }
}
```

#### Mixing filter and map nodes

It is possible using more than one kind of node in the same pipeline.

``` java
public class Main() {
    public static void main(String[] args) {
        
        List<Integer> output = new ArrayList<>();
    
        Pipeline<Integer> pipeline = new Stream<>(list())
                        .map(String::length)
                        .filter(l -> l > 5)
                        .collectTo(output);
    }
    
    private static List<String> list() {
        return Arrays.asList("Hello this is a prove to check if everything went correctly".split(" "));
    }
}
```

### Change NodeContainer

All the written code until here will be executed locally. However, **Wonderfunc is built to execute a distributed pipeline in different functional cloud computing platforms**. In order to change the node container where lambda expressions will be executed, just use the `on` method passing as a parameter an object that implements the created interface `NodeContainer`. Initially, **a LocalNodeContainer class has been implemented** to execute locally the pipeline. **This is the default NodeContainer**. Nevertheless, Wonderfunc is an open source framework. This means that anybody can implement and add new classes implementing the NodeContainer interface, adding new functionality to Wonderfunc.

As an example, a LocalNodeContainer will be added explicitly to show how this works.

``` java
public class Main() {
    public static void main(String[] args) {
        
        List<Integer> output = new ArrayList<>();
    
        Pipeline<Integer> pipeline = new Stream<>(list())
                        .on(new LocalNodeContainer())
                            .map(String::length)
                            .filter(l -> l > 5)
                        .collectTo(output);
    }
    
    private static List<String> list() {
        return Arrays.asList("Hello this is a prove to check if everything went correctly".split(" "));
    }
}
```

It is possible to execute a pipeline's section in one platform and another different section in other platform. Here is an example showing how to change more than once the NodeContainer. This example it is assumed that an AWS NodeContainer has been implemented called `AWS`.

``` java
public class Main() {
    public static void main(String[] args) {
        
        List<Integer> output = new ArrayList<>();
    
        Pipeline pipeline = new Stream<>(list())
                        .on(new LocalNodeContainer())
                            .map(String::length)
                        .on(new AWS("aws_access_key_id", "aws_secret_access_key"))    
                            .filter(l -> l > 5)
                        .collectTo(output);
    }
    
    private static List<String> list() {
        return Arrays.asList("Hello this is a prove to check if everything went correctly".split(" "));
    }
}
```

### Using FunctionRepository functions

Apart from executing lambda functions created in execution time in different node containers, Wonderfunc is prepared to use already created functions placed in what has received the name of `FunctionRepository`. To use functions located in a function repository, an object implementing the interface `FunctionRepository` needs to be created. Once that is completed, the function should be selected using the method `function` in the `FunctionRepository` interface and use the function that it returns inside the lambda expression in a map node.

``` java
public class Main() {
    public static void main(String[] args) {
        
        List<Boolean> output = new ArrayList<>();
        
        FunctionRepository algorithmia = new Algorithmia("YOUR_API_KEY");
        Function<String, String> sentimentAnalysis = algorithmia.function("nlp/SentimentAnalysis/1.0.5");
    
        Pipeline<Boolean> pipeline = new Stream<>(list())
                        .on(new LocalNodeContainer())
                            .map(s -> sentimentAnalysis.apply(s))
                                .with(new RemoteExpressionExecutor(new CommentMarshalling()))
                            .map(d -> d <= 0)
                        .collectTo(output);
    }
    
    private static List<String> list() {
        return Arrays.asList("Hello this is a prove to check if everything went correctly".split(" "));
    }
}
```

[Algorithmia](https://algorithmia.com) is a platform that contains a great amount of lambda functions that implements different algorithms including Machine Learning algorithms.
 
To execute functions that are located in a function repository, it is compulsory specifying that a `RemoteExpressionExecutor` will be used to execute asynchronously that function using the `with` method.  

### Pipeline Execution

To execute the Pipeline, once it has been built with the `collectTo` method, just call the `execute` method.

``` java
public class Main() {
    public static void main(String[] args) {
        
        List<Integer> output = new ArrayList<>();
    
        Thread pipelineThread = new Stream<>(list())
                                    .on(new LocalNodeContainer())
                                        .map(String::length)
                                        .filter(l -> l > 5)
                                    .collectTo(output)
                                    .execute();
    }
    
    private static List<String> list() {
        return Arrays.asList("Hello this is a prove to check if everything went correctly".split(" "));
    }
}
```

This project has been developed together with José Juan Hernández Cabrera and José Évora.
