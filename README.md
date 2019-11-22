部署步骤：
    1.后台调整
        修改CreateDirectory中的directory
        logback-spring.xml
            修改日志级别：fileErrorApp
            修改目录：    d:/zybFile
        ureport.properties
            ureport.debug=false
        注释掉application.yml的log-impl
        修改application.yml的数据库的IP
    2.前端整合
        2.1将dist下的index.html拷贝到templates
            在index.html中添加命名空间<html xmlns:th="http://www.thymeleaf.org">
        2.2将css、js、ico文件拷贝到static，注意，将dist->static中的所有文件也拷贝到static
        2.3双击gradle -> Tasks -> build -> build
            (注意：bootWar、War等双击打出的War无效)
        2.4拷贝build -> libs 下的zyb.war即可