package com.biao.mall.common.mbp;


import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Classname MpgGenerator
 * @Description MBP
 * @Author xiexiaobiao
 * @Date 2019-10-04 12:06
 * @Version 1.0
 **/
public class MbpGenerator {

    public static String scanner(String tip){
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("please input " + tip + ":");
        System.out.println(help.toString());
        if (scanner.hasNext()){
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)){
                return ipt;
            }
        }
        throw new MybatisPlusException("please input correct string:" + tip + "!");
    }

    public static void main(String[] args) {

        AutoGenerator mpg = new AutoGenerator();

        //global config
        GlobalConfig gc = new GlobalConfig();
        //获取当前项目的路径
        String projectPath = System.getProperty("user.dir");
        System.out.println("projectPath: "+projectPath);//for test
        //重点--> 设置文件生成的总路径
        gc.setOutputDir(projectPath + "/account/src/main/java");
        gc.setAuthor("XieXiaobiao");
        gc.setFileOverride(false); //是否覆盖原来的文件
        gc.setBaseColumnList(true); // XML columList
        gc.setBaseResultMap(true);
        gc.setOpen(false);
        /*note the  %s will auto replaced by table attributes*/
        //重点--> 文件名设置
        gc.setEntityName("%sEntity");
        gc.setMapperName("%sDao"); //有的项目定义为Mapper文件，有的定义为Dao文件，一样的
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        mpg.setGlobalConfig(gc);

        //数据源设置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/seata_pay?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        //包设置
        PackageConfig pc = new PackageConfig();
        //单独生成模块时使用，不需要的话直接注释掉
//        pc.setModuleName(scanner("module-name")); // eg: com.example.common.{module-name}.dao
        pc.setParent("com.biao.mall.account"); //
        pc.setEntity("entity");
        pc.setMapper("dao");
        pc.setService("service");
        pc.setServiceImpl("impl");
        pc.setController("controller");
        pc.setXml("mapper");
        mpg.setPackageInfo(pc);

        //other customized config
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
/*                Map<String, Object> map = new HashMap<>();
//                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);*/
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        /**/
/*        focList.add(new FileOutConfig("/templates/mapper.xml"){
            // 自定义输出文件目录
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/common/src/main/resources/mapper/" // + pc.getModuleName() + "/"
                       + tableInfo.getEntityName() + "Mapper";
            }
        });*/
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        // close  xml generation,and will save at the root directory

        //模板设置,这里的模板是指mapper.xml文件，不是web前端模板
        TemplateConfig template = new TemplateConfig();
        //不需要生成模板，则直接打开下面的
//        template.setXml(null);
        mpg.setTemplate(template);

        //策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setEntityBuilderModel(true);
//        strategy.setSuperEntityClass("com.baomidou.mybatisplus.samples.generator.common.BaseEntity");
        strategy.setEntityLombokModel(true);
//        strategy.setSuperControllerClass("com.baomidou.mybatisplus.samples.generator.common.BaseController");
        strategy.setInclude(scanner("table-name"));//需要包含的表名，允许正则表达式（与exclude二选一配置）
//        strategy.setExclude((String) null);//此句生效则生成所有表的对象
//        strategy.setSuperEntityColumns(""); //父类的公共字段
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        // if use freemarker, be aware the dependency is required
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
