package com.deng.bitpic.utils;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @description: IPFS文件传输
 * @author: Deng
 * @create: 2019-02-11
 */
public class IpfsUtil {

    public static final IPFS  IPFS_CLIENT = new IPFS("/ip4/127.0.0.1/tcp/5001");

    /**
     * 上传文件到ipfs网络
     * @param filePathName 文件目录 + 文件名
     * @return ipfs地址
     * @throws IOException
     */
    public static String upload(String filePathName) throws IOException {
        NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File(filePathName));
        MerkleNode addResult = IPFS_CLIENT.add(file).get(0);
        return addResult.hash.toString();
    }

    /**
     * 从ipfs网络下载文件
     * @param hash ipfs地址
     * @param filePathName 文件保存目录 + 文件名
     * @throws IOException
     */
    public static void download(String hash, String filePathName) throws IOException{
        Multihash filePointer = Multihash.fromBase58(hash);
        byte[] data = IPFS_CLIENT.cat(filePointer);

        if (data != null) {
            File file = new File(filePathName);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data,0,data.length);
            fos.flush();
            fos.close();
        }
    }
}
