package com.helloworld.hrouter.compiler;

import com.google.auto.service.AutoService;
import com.helloworld.hrouter.annotation.Route;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class RouterProcessor extends AbstractProcessor {
    private Messager messager;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> sets = new HashSet<>();
        sets.add(Route.class.getCanonicalName());
        return sets;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        messager.printMessage(Diagnostic.Kind.NOTE,"sssssssssssssss --------------->>>>>>>>>>>>");
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Route.class);

        //1. 获取到所有使用了Route注解的activity，并存入表中
        Map<String,String> map = new HashMap<>();
        for (Element element : elements ) {
            TypeElement typeElement = (TypeElement) element;
            Name qualifiedName = typeElement.getQualifiedName();
            messager.printMessage(Diagnostic.Kind.NOTE,"qualifiedName=" + qualifiedName.toString());

            Route annotation = typeElement.getAnnotation(Route.class);
            String value = annotation.value();
            messager.printMessage(Diagnostic.Kind.NOTE,"value=" + value);
            map.put(value,qualifiedName.toString() + ".class");
        }

        //写文件
        if (!map.isEmpty()) {
            ClassName className = ClassName.get("com.helloworld.hrouter.core","IRouter");
            TypeSpec.Builder typeBuilder = TypeSpec.classBuilder("ActivityUtil" + System.currentTimeMillis())
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(className);

            MethodSpec.Builder addActivityBuilder = MethodSpec.methodBuilder("addActivity")
                    .addAnnotation(ClassName.get(Override.class))
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.VOID);
            for (String key : map.keySet()) {
                String value = map.get(key);
                String s = String.format("$T.getInstance().addActivity(\"%s\",%s)",key,value);
                addActivityBuilder.addStatement(s,ClassName.get("com.helloworld.hrouter.core","HRouter"));
            }

            TypeSpec activityUtil = typeBuilder.addMethod(addActivityBuilder.build()).build();
            JavaFile file = JavaFile.builder("com.helloworld.util", activityUtil).build();

            try {
                file.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
