### nacos example(spring-cloud集成)

+ nacos discovery
+ nacos config


### 备注
nacos[官方文档](https://nacos.io/zh-cn/docs/quick-start-spring-cloud.html)

服务端下载[地址](https://github.com/alibaba/nacos/releases)，版本 ```1.0.0(Apr 10, 2019)```，配置默认在```derby```中，生成需要替换为```mysql```

spring-boot版本
```xml
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.5.RELEASE</version>
        <relativePath/>
    </parent>
```

spring-cloud对应的版本
```xml
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Greenwich.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>0.2.1.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

### 重点关注
```com.alibaba.nacos.api.NacosFactory``` 核心类，用于创建```NacosNamingService```和```NacosConfigService```，维持客户端和服务端之间的数据通信、容灾。

### 相关知识
需要了解```EventDispatcher```事件分发组件。
