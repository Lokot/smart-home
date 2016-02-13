tomee.xml add
<Resource id="smartHomeDataSource" type="javax.sql.DataSource">
    accessToUnderlyingConnectionAllowed = false
    alternateUsernameAllowed = false
    connectionProperties = 
    defaultAutoCommit = true
    defaultReadOnly = 
    definition = 
    ignoreDefaultValues = false
    initialSize = 0
    jdbcDriver = org.hsqldb.jdbcDriver
    jdbcUrl = jdbc:hsqldb:mem:hsqldb
    jtaManaged = false
    maxActive = 20
    maxIdle = 20
    maxOpenPreparedStatements = 0
    maxWaitTime = -1 millisecond
    minEvictableIdleTime = 30 minutes
    minIdle = 0
    numTestsPerEvictionRun = 3
    password = 12345
    passwordCipher = PlainText
    poolPreparedStatements = false
    serviceId = 
    testOnBorrow = true
    testOnReturn = false
    testWhileIdle = false
    timeBetweenEvictionRuns = -1 millisecond
    userName = sa
    validationQuery = 
</Resource>