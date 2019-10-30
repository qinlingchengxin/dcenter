通过设置ip白名单限制中心上传、下载数据
mysql需要设置表名字段名忽略大小写
lower_case_table_names=1

maven settings.xml文件中需要添加镜像

<mirror>
    <id>bj-repo</id>
    <mirrorOf>central</mirrorOf>
    <name>Nexus osc</name>
    <url>http://10.30.30.56:8081/nexus/content/groups/public/</url>
</mirror>

附件下载地址：
    http://localhost:8080/admin/api/down/file.do?id=xxx