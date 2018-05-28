package application;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author omerbatmaz@gmail.com May 2018
 *
 *  http://localhost:8080/fourth/fourth/rest/findById/1
 *
 *  TODO Make and refactor related codes
 *
 *      Add custom exception class
 *      Add custom response class which is defined status - data - count etc. seperate
 */
@ApplicationPath("fourth")
public class PersonApplication extends Application{

}
