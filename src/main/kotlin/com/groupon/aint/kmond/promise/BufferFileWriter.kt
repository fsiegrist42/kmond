/**
 * Copyright 2015 Groupon Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.groupon.aint.kmond.promise

import com.groupon.promise.AsyncPromiseFunction
import com.groupon.promise.DefaultPromiseFuture
import com.groupon.promise.PromiseFuture
import io.vertx.core.buffer.Buffer
import io.vertx.core.file.FileSystem
import java.io.File

/**
 * Asynchronously writes a buffer to a file, passes along the absolute path on success.
 *
 * @author Gil Markham (gil at groupon dot com)
 */
class BufferFileWriter(val fileSystem: FileSystem, val directory: File, val filename: String): AsyncPromiseFunction<Buffer, String> {
    override fun handle(buffer: Buffer?): PromiseFuture<out String> {
        val future = DefaultPromiseFuture<String>()
        val absolutePath = File(directory, filename).absolutePath
        if (buffer != null) {
            fileSystem.writeFile(absolutePath, buffer) { result ->
                if (result.succeeded()) {
                    future.setResult(absolutePath)
                } else {
                    future.setFailure(result.cause())
                }
            }
        } else {
            future.setFailure(IllegalArgumentException("null not allowed"))
        }
        return future
    }
}
