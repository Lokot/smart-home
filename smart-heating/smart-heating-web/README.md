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
