@Echo off


rem Usage: SOLUTIONS/SETS <input files dir> <input files pref> <input files ext> <solutions dir> <solutions files pref> <solutions files ext>
rem <findFirstSolutionOnly (true/false)> <findUniqueSolutionsOnly (true/false)>

rem java  -jar ./target/happycube-spring-boot.jar SOLUTIONS ./data blue txt ./solutions/blue_nu solution sol false false

java  -jar ./target/happycube-spring-boot.jar  SETS ./solutions/sets solution sol 

rem java  -jar ./target/happycube-spring-boot.jar SOLUTIONS ./data red txt ./solutions/red solution sol true true

rem java  -jar ./target/happycube-spring-boot.jar SOLUTIONS ./data lilac txt ./solutions/lilac_nu solution sol false false

rem java  -jar ./target/happycube-spring-boot.jar SOLUTIONS ./data yellow txt ./solutions/yellow solution sol false true