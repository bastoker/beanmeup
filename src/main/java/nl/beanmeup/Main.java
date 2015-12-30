package nl.beanmeup;

        import ratpack.guice.Guice;
        import ratpack.handlebars.HandlebarsModule;
        import ratpack.server.BaseDir;
        import ratpack.server.RatpackServer;

        import java.io.File;

        import static ratpack.handlebars.Template.handlebarsTemplate;

public class Main {
    public static void main(String... args) throws Exception {
        RatpackServer.of(spec -> spec.serverConfig(c -> c.baseDir(BaseDir.find()))
                .registry(Guice.registry(b -> b.module(HandlebarsModule.class)))
                .handlers(chain -> chain
                        .get(ctx -> ctx.render(handlebarsTemplate("index.html")))
                        .get(":templatename.html",
                                ctx -> ctx.render(handlebarsTemplate(ctx.getPathTokens().get("templatename") + ".html",
                                        m -> m.put("name", ctx.getPathTokens().get("name") + "!"))))
                        .files(f -> f.dir("public"))
                )).start();
    }
}