1. springmvc视图解析就是将Controller返回的视图不利用默认的解析方式，即不是前面配置的那种 前缀+返回值+后缀 的方式，而是利用XML配置进行视图解析
2. 以前用的配置如下
```
<!--这里为Servlet.xml配置web.xml的配置相同-->
<!--注：这里只是部分关于视图解析的配置，其他配置自行添加-->
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!--prefix 前缀-->
        <property name="prefix">
            <value>/WEB-INF/jsp/</value>
        </property>
        <!--suffix 后缀-->
        <property name="suffix">
            <value>.jsp</value>
        </property>
    </bean>
```
3. xml视图解析配置
```
<!--Servlet.xml配置 部分-->
<bean class="org.springframework.web.servlet.view.XmlViewResolver">
    <!--这里的name指定为location，还有其他几个值，碰到可自行处理-->
        <property name="location">
           <!--指定解析的xml文件的位置--> <value>/WEB-INF/view.xml</value>
        </property>
    </bean>
    
<!--在对应位置建立xml配置-->
<!--对应的view.xml配置如下-->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!--id为返回的字符串-->
    <bean id="hello" class="org.springframework.web.servlet.view.JstlView">
    <!--这里的name同样有几个对应的值  value则对应的的视图名称-->
        <property name="url" value="/WEB-INF/jsp/hello.jsp"/>
    </bean>
</beans>
```

4. 静态资源绑定视图解析，配置如下
```
<!--Servlet.xml的配置如下-->
<!--资源绑定视图-->
    <bean class="org.springframework.web.servlet.view.ResourceBundleViewResolver">
        <!--basename 表示携带资源束的名称  value表示其对应的值 其对应的配置文件为views.properties如果值改变了对应的配置文件名也应更改-->
        <property name="basename" value="views" />
    </bean>

<!--views.properties的位置在src文件夹下-->
<!--内容如下-->
#这里和xml对应的配置相似
hello.(class)=org.springframework.web.servlet.view.JstlView
hello.url=/WEB-INF/jsp/hello.jsp
```
5. 多视图解析，就是将多种视图解析集成到一个项目中，例如可以将上面的三种解析方式集成到一个项目中，只需要在配置servlet中的解析bean的时候加上如下代码：
```
<property name="order" value="0"/>
<!--其中value的值可以为0，1，2，3.....-->

<!--总的配置如下-->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="
   http://www.springframework.org/schema/beans
   http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
   http://www.springframework.org/schema/context
   http://www.springframework.org/schema/context/spring-context-3.0.xsd
   http://www.springframework.org/schema/mvc
   http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <context:component-scan base-package="com.liu.springmvc" />
    <!--一般配置-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!--prefix 前缀-->
        <property name="prefix">
            <value>/WEB-INF/jsp/</value>
        </property>
        <!--suffix 后缀-->
        <property name="suffix">
            <value>.jsp</value>
        </property>
        <!--上面以及下面的配置也可以用如下的方式-->
        <!--<property name="order" value="0"/>-->
        <!--加上下面这个视图解析排序即可完成多视图解析 在前一个解析方法没有扫描到视图的情况下会执行后面的视图解析进行最终解析-->
        <property name="order">
            <value>0</value>
        </property>
    </bean>
    <!--xml视图解析配置-->
    <bean class="org.springframework.web.servlet.view.XmlViewResolver">
        <property name="location">
            <value>/WEB-INF/view.xml</value>
        </property>
        <property name="order">
            <value>1</value>
        </property>
    </bean>

    <!--资源绑定视图-->
    <bean class="org.springframework.web.servlet.view.ResourceBundleViewResolver">
        <!--basename 表示携带资源束的名称  value表示其对应的值 其对应的配置文件为views.properties-->
        <property name="basename" value="views" />
        <property name="order" value="2"/>
    </bean>

    <!--申请静态资源文件使用的配置-->
    <mvc:resources mapping="/jsp/**" location="/WEB-INF/jsp/"/>
    <mvc:annotation-driven/>
    <!--这里使用<mvc:resources ..../>标记来映射静态页面。映射属性必须是指定http请求的URL模式的Ant模式。location属性必须指定一个或多个有效的资源目录位置，其中包含静态页面，包括图片，样式表，JavaScript和其他静态内容。可以使用逗号分隔的值列表指定多个资源位置。-->
</beans>
<!--其他需要依赖的配置可以查看上面的配置方式的依赖配置-->
```