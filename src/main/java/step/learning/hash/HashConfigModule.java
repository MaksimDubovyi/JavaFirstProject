package step.learning.hash;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import step.learning.ioc.*;

public class HashConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        //звя'зування за типом
      //  bind(IHashservice.class).to(Md5Hashservice.class);


        //іменоване зв'язування
        bind(IHashservice.class).annotatedWith(Names.named("md5")).to(Md5Hashservice.class);
        bind(IHashservice.class).annotatedWith(Names.named("sha1")).to(ShaHashService.class);
        bind(IHashservice.class).annotatedWith(Names.named("sha256")).to(Sha256HashService.class);
        bind(IHashservice.class).annotatedWith(Names.named("sha512")).to(Sha512HashService.class);
        bind(IHashservice.class).annotatedWith(Names.named("argon")).to(Argon2HashService.class);
    }
}
