/*
 * Copyright 2018 ABSA Group Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package Assisted_Drive

import java.util.ServiceLoader

import org.scalatest.{FlatSpec, Matchers}
import za.co.absa.hyperdrive.ingestor.api.reader.StreamReaderFactoryProvider
import za.co.absa.hyperdrive.ingestor.api.transformer.StreamTransformerFactoryProvider
import za.co.absa.hyperdrive.ingestor.api.writer.StreamWriterFactoryProvider
import Assisted_Drive.reader.mycomponent.MyStreamReaderImpl
import Assisted_Drive.transformer.mycomponent.MyStreamTransformerImpl
import Assisted_Drive.writer.mycomponent.MyStreamWriterImpl

class ServiceProviderConfigurationTest extends FlatSpec with Matchers {
  behavior of "Service Provider Configuration (META-INF/services)"

  it should "load configured factories" in {
    val classLoader = this.getClass.getClassLoader
    import scala.collection.JavaConverters._

    val readerFactory = ServiceLoader.load(classOf[StreamReaderFactoryProvider], classLoader).asScala
      .map(_.getComponentFactory).toList
    readerFactory should contain only MyStreamReaderImpl

    val transformerFactory = ServiceLoader.load(classOf[StreamTransformerFactoryProvider], classLoader).asScala
      .map(_.getComponentFactory).toList
    transformerFactory should contain only MyStreamTransformerImpl

    val writerFactory = ServiceLoader.load(classOf[StreamWriterFactoryProvider], classLoader).asScala
      .map(_.getComponentFactory).toList
    writerFactory should contain only MyStreamWriterImpl
  }
}
