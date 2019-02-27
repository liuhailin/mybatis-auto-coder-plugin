/**
 *
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Properties;

import org.mybatis.generator.api.XmlFormatter;
import org.mybatis.generator.api.dom.DefaultXmlFormatter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.internal.JDBCConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 生成基础代码, 注意两个配置文件: code/generatorConfig.xml, code/mybatis.onproperties
 */
public class CodeUtilGenTable {

    static Logger logger = LoggerFactory.getLogger( CodeUtilGenTable.class );

    /**
     * @param args
     */
    public static void main(String[] args) {
        //
        try {
            Properties properties = System.getProperties();
            properties.load(
                CodeUtilGenTable.class.getClassLoader().getResourceAsStream( "code/mybatis_db.properties" ) );

            XmlFormatter xmlFormatter = new DefaultXmlFormatter();
            Document document = getDocument();

            JDBCConnectionFactory factory = new JDBCConnectionFactory();
            factory.addConfigurationProperties( properties );

            Connection connection = factory.getConnection();
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables( null, null, null, new String[] {"TABLE"} );
            while (rs.next()) {
                String tableName = rs.getString( "TABLE_NAME" );
                String tableType = rs.getString( "TABLE_TYPE" );
                String domainObjectName = tableName;
                //if (domainObjectName.startsWith("t_")) {
                //    domainObjectName = domainObjectName.substring(2);
                //}
                //if (domainObjectName.startsWith("tb_")) {
                //    domainObjectName = domainObjectName.substring(3);
                //}
                logger.info( "表名: <{}>; 表类型 : <{}>", tableName, tableType );

                XmlElement table = new XmlElement( "table" ); //$NON-NLS-1$
                table.addAttribute( new Attribute( "tableName", tableName ) );
                table.addAttribute( new Attribute( "schema", "general" ) );
                table.addAttribute( new Attribute( "enableCountByExample", "false" ) );
                table.addAttribute( new Attribute( "enableUpdateByExample", "false" ) );
                table.addAttribute( new Attribute( "enableDeleteByExample", "false" ) );
                table.addAttribute( new Attribute( "enableSelectByExample", "false" ) );
                table.addAttribute( new Attribute( "selectByExampleQueryId", "false" ) );

                ResultSet pk = md.getPrimaryKeys( null, null, tableName );
                while (pk.next()) {
                    String columnName = pk.getString( "COLUMN_NAME" ); //$NON-NLS-1$
                    short keySeq = pk.getShort( "KEY_SEQ" ); //$NON-NLS-1$
                    logger.info( "\t 表名: <{}>; 主键 : <{}>; keySeq: <{}>", tableName, columnName, keySeq );

                    XmlElement generatedKey = new XmlElement( "generatedKey" ); //$NON-NLS-1$
                    generatedKey.addAttribute( new Attribute( "column", columnName ) );
                    generatedKey.addAttribute( new Attribute( "sqlStatement", "JDBC" ) );
                    generatedKey.addAttribute( new Attribute( "identity", "true" ) );
                    table.addElement( generatedKey );
                    if (keySeq == 1) {
                        break;
                    }
                    // md.getColumns(catalog, schemaPattern, tableNamePattern,
                    // columnNamePattern)
                }

                document.getRootElement().addElement( table );
            }
            connection.close();

            String dir = System.getProperty( "user.dir" );
            File f = new File( dir, "/" );
            if (!f.exists()) {
                f.mkdirs();
            }
            File targetFile = new File( f, "add_to_generator.xml" );
            String source = xmlFormatter.getFormattedContent( document );

            writeFile( targetFile, source, "UTF-8" );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void writeFile(File file, String content, String fileEncoding) throws IOException {
        logger.info( "write to file: <{}>; ", file );
        FileOutputStream fos = new FileOutputStream( file, false );
        OutputStreamWriter osw;
        if (fileEncoding == null) {
            osw = new OutputStreamWriter( fos );
        } else {
            osw = new OutputStreamWriter( fos, fileEncoding );
        }

        BufferedWriter bw = new BufferedWriter( osw );
        bw.write( content );
        bw.close();
    }

    public static Document getDocument() {
        Document document = new Document( XmlConstants.MYBATIS_GENERATOR_CONFIG_PUBLIC_ID,
            XmlConstants.MYBATIS_GENERATOR_CONFIG_SYSTEM_ID );
        XmlElement answer = new XmlElement( "generatorConfiguration" ); //$NON-NLS-1$
        document.setRootElement( answer );

        return document;
    }

}
