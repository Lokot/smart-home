https://vaadin.com/tutorial
https://vaadin.com/directory#!addon/loginform
https://dev.vaadin.com/svn/doc/book-examples/trunk/src/com/vaadin/book/examples/addons/jpacontainer/JPAContainerExample.java


tomee.xml add
<Resource id="smartHomeDataSource" type="DataSource">
		JdbcDriver  org.hsqldb.jdbcDriver
		JdbcUrl jdbc:hsqldb:mem:hsqldb
		UserName    root
		Password    some_pass
		JtaManaged true
	</Resource>

	
	Профили сборки:
	1) tomee - по умолчанию для Tomee (без hibernate-validator-*.jar и validation-api-*.jar)
	2) owfs - по умолчанию для взаимодействия с OWFS-сервером
	3) owapi - для взаимодействия с адаптером DS9490 через JNI
	4) buildVaadin - для пересборки Vaadin