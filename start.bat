@Echo off


rem Usage: <input files dir> <input files pref> <input files ext> <solutions dir> <solutions files pref> <solutions files ext> 
rem <findFirstSolutionOnly (true/false)> <findUniqueSolutionsOnly (true/false)>

java  -jar ./target/happycube-spring-boot.jar ./data blue txt ./solutions/blue solution sol false true

rem java  -jar ./target/happycube-spring-boot.jar ./data red txt ./solutions/red solution sol true true

rem java  -jar ./target/happycube-spring-boot.jar ./data lilac txt ./solutions/lilac_new1 solution sol false true

rem java  -jar ./target/happycube-spring-boot.jar ./data yellow txt ./solutions/yellow solution sol false true