@echo off
REM MyPluginApp

setlocal

SET CLASSPATH=%~dp0..;%~dp0..\bin\;%~dp0..\conf\;%~dp0..\lib\*;%CLASSPATH%
REM java -Xmx3550m -Xms3550m -XX:PermSize=128m -XX:MaxPermSize=614M -classpath ..;..\conf\;..\bin\;..\lib\ttsoft-main.jar;..\lib\asm-3.3.jar;..\lib\asm-commons-3.3.jar;..\lib\asm-tree-3.3.jar;..\lib\aspectjrt.jar;..\lib\aspectjweaver.jar;..\lib\cglib-2.2.2.jar;..\lib\com.springsource.net.sf.ehcache-1.6.2.jar;..\lib\com.springsource.org.aopalliance-1.0.0.jar;..\lib\commons-beanutils-1.8.3.jar;..\lib\commons-collections-3.1.jar;..\lib\commons-fileupload-1.3.jar;..\lib\commons-io-2.0.1.jar;..\lib\commons-lang-2.4.jar;..\lib\commons-lang3-3.1.jar;..\lib\commons-logging-1.1.3.jar;..\lib\dom4j-1.6.1.jar;..\lib\ezmorph-1.0.3.jar;..\lib\freemarker-2.3.19.jar;..\lib\ibatis-2.3.0.677.jar;..\lib\javassist-3.17.1-GA.jar;..\lib\jaxen-1.1.3.jar;..\lib\json-lib-2.3-jdk15.jar;..\lib\jsp-api.jar;..\lib\juli-6.0.18.jar;..\lib\log4j-1.2.17.jar;..\lib\ognl-3.0.6.jar;..\lib\ojdbc14.jar;..\lib\org.apache.felix.framework-4.2.1.jar;..\lib\org.apache.felix.main-4.2.1.jar;..\lib\org.apache.felix.shell-1.4.3.jar;..\lib\org.osgi.compendium-4.0.0.jar;..\lib\org.osgi.core-4.1.0.jar;..\lib\quartz-all-1.8.6.jar;..\lib\serializer.jar;..\lib\servlet-api.jar;..\lib\slf4j-api-1.7.5.jar;..\lib\slf4j-log4j12-1.7.5.jar;..\lib\spring-aop-3.2.3.RELEASE.jar;..\lib\spring-aspects-3.2.3.RELEASE.jar;..\lib\spring-beans-3.2.3.RELEASE.jar;..\lib\spring-context-3.2.3.RELEASE.jar;..\lib\spring-context-support-3.2.3.RELEASE.jar;..\lib\spring-core-3.2.3.RELEASE.jar;..\lib\spring-expression-3.2.3.RELEASE.jar;..\lib\spring-jdbc-3.2.3.RELEASE.jar;..\lib\spring-jms-3.2.3.RELEASE.jar;..\lib\spring-orm-3.2.3.RELEASE.jar;..\lib\spring-oxm-3.2.3.RELEASE.jar;..\lib\spring-patch-quartz.jar;..\lib\spring-security-acl-3.1.4.RELEASE.jar;..\lib\spring-security-aspects-3.1.4.RELEASE.jar;..\lib\spring-security-cas-3.1.4.RELEASE.jar;..\lib\spring-security-config-3.1.4.RELEASE.jar;..\lib\spring-security-core-3.1.4.RELEASE.jar;..\lib\spring-security-crypto-3.1.4.RELEASE.jar;..\lib\spring-security-ldap-3.1.4.RELEASE.jar;..\lib\spring-security-openid-3.1.4.RELEASE.jar;..\lib\spring-security-remoting-3.1.4.RELEASE.jar;..\lib\spring-security-web-3.1.4.RELEASE.jar;..\lib\spring-tx-3.2.3.RELEASE.jar;..\lib\spring-web-3.2.3.RELEASE.jar;..\lib\spring-webmvc-3.2.3.RELEASE.jar;..\lib\struts2-core-2.3.15.jar;..\lib\struts2-embeddeding-plugin-2.3.15.jar;..\lib\struts2-json-plugin-2.3.15.jar;..\lib\struts2-osgi-plugin-2.3.15.jar;..\lib\ttsoft-osgi.jar;..\lib\xalan.jar;..\lib\xercesImpl.jar;..\lib\xml-apis.jar;..\lib\xwork-core-2.3.15.jar ttsoft.main.Main

set JAVA_OPTS=%JAVA_OPTS% -Xmx1024m -Xms128m -XX:PermSize=64m -XX:MaxPermSize=128M

set APPMAIN=ttsoft.main.Main

echo on
java %JAVA_OPTS% -cp "%CLASSPATH%" "%APPMAIN%" %*

endlocal
