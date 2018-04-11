package main.scala

import com.google.inject.name.{Named, Names}
import com.google.inject.{AbstractModule, Guice, Inject}
import net.codingwell.scalaguice.ScalaModule
import net.codingwell.scalaguice.InjectorExtensions._


trait DataSet
case class LBDataSet() extends DataSet
case class ISPDataSet() extends DataSet

trait JsonDeserializer[A]
case class LBJsonDeserializer() extends JsonDeserializer[LBDataSet] {
  println("............... LB Json Deserializer init .............")
}
case class ISPJsonDeserializer() extends JsonDeserializer[ISPDataSet] {
  println("............... ISP Json Deserializer init .............")
}

class DataSetClient[A <: DataSet] @Inject()(@Named("lbDeserializer") deserializer: JsonDeserializer[A]) {
  println(" Dataset client init")
}


trait CitationDatasetResolver

//@Inject() (@Named(Foo.NAME) something: Something)
class LBCitationDatasetResolver[A <: DataSet] @Inject()(@Named("lbClient") dataSetClient: DataSetClient[A]) extends CitationDatasetResolver {


  println("@@@@ LB DataSet resolver invoked @@@")
}
/*
class IspCitationDatasetResolver[A <: DataSet](@Named("ispClient") dataSetClient: DataSetClient[A]) extends CitationDatasetResolver {
  println("@@@@ ISP DataSet resolver invoked @@@")
}
*/

class Module extends AbstractModule with ScalaModule {
  def configure() = {

    //     import net.codingwell.scalaguice.InjectorExtensions._

    /*bind(classOf[Hello])
      .annotatedWith(Names.named("en"))
      .to(classOf[EnglishHello])

    bind(classOf[Hello])
      .annotatedWith(Names.named("de"))
      .to(classOf[GermanHello])*/

    bind[JsonDeserializer[LBDataSet]].annotatedWith(Names.named("lbDeserializer")).to[LBJsonDeserializer]

    bind[DataSetClient[LBDataSet]].annotatedWith(Names.named("lbClient")).to[DataSetClient[LBDataSet]]
//    bind[DataSetClient[ISPDataSet]].annotatedWith(Names.named("ispClient")).to[DataSetClient[ISPDataSet]]
   // bind[JsonDeserializer[ISPDataSet]].annotatedWith(Names.named("ispTransformer")).to[ISPJsonDeserializer]

    bind[LBCitationDatasetResolver[LBDataSet]].annotatedWith(Names.named("lbDataSetResolver")).to[LBCitationDatasetResolver[LBDataSet]]
  //  bind[IspCitationDatasetResolver[ISPDataSet]].annotatedWith(Names.named("ispDataSetResolver")).to[IspCitationDatasetResolver[ISPDataSet]]


  }
}

object MyServer {
  def main(args: Array[String]) {
    val injector = Guice.createInjector(new Module())

    //val serializer = injector.instance[JsonDeserializer[LBDataSet]](Names.named("lbTransformer"))

   // val x = injector.instance[LBCitationDatasetResolver[LBDataSet]]

    val serializer = injector.instance[LBCitationDatasetResolver[LBDataSet]](Names.named("lbDataSetResolver"))
 //   val serializer = injector.instance[IspCitationDatasetResolver[ISPDataSet]](Names.named("ispDataSetResolver"))


    /*val service = injector.instance[Service]
    val foo = injector.instance[Foo]

    // Retrieve a Bar annotated with Transactional
    val bar = injector.instance[Bar, Transactional]

    // Retrieve a PaymentService annotated with a specific Annotation instance.
    val paymentService = injector.instance[PaymentService](Names.named("paypal"))
    ...*/
  }
}




/*
class Factory {

  def getResolver(datasetType: String): CitationDatasetResolver = {



   // val lBDataSetClient = new DataSetClient[LBDataSet](LBJsonDeserializer())
    //val ispDataSetClient = new DataSetClient[ISPDataSet](ISPJsonDeserializer())

    /*datasetType match {
      case "A" => {

        new IspCitationDatasetResolver[ISPDataSet](ispDataSetClient)
      }
      case "B" => {
        new LBCitationDatasetResolver[LBDataSet](lBDataSetClient)
      }
      case _ => throw new RuntimeException(s"Resolver not found for dataset $datasetType")
    }*/
  }
}
*/