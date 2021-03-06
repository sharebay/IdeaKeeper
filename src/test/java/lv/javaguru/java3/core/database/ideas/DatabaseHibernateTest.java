package lv.javaguru.java3.core.database.ideas;

import lv.javaguru.java3.config.Application;
import lv.javaguru.java3.core.database.*;
import org.hibernate.SessionFactory;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})

//@IntegrationTest("server.port:0")
public abstract class DatabaseHibernateTest {

	@Autowired
	protected SessionFactory sessionFactory;

    @Autowired
    protected IdeaDAO ideaDAO;

    @Autowired
    protected UserDAO userDAO;

}
