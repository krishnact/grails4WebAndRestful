import org.spring.security.UserPasswordEncoderListener
// Place your Spring DSL code here
beans = {
    userPasswordEncoderListener(UserPasswordEncoderListener, ref('hibernateDatastore'))
}
