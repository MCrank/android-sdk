package com.mobilecoin.lib;


import android.util.Log;

import com.mobilecoin.lib.exceptions.InvalidTxOutMemoException;
import com.mobilecoin.lib.util.Hex;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TxOutMemoParserTest {

  private static final String TAG = "TxOutMemoParserTest";
  private static final String senderAccountKeyHexProtoBytes = "0a220a20553a1c51c1e91d3105b17c909c163f8bc6faf93718deb06e5b9fdb9a24c2560912220a20db8b25545216d606fc3ff6da43d3281e862ba254193aff8c408f3564aefca5061a1e666f673a2f2f666f672e616c7068612e6d6f62696c65636f696e2e636f6d2aa60430820222300d06092a864886f70d01010105000382020f003082020a0282020100c853a8724bc211cf5370ed4dbec8947c5573bed0ec47ae14211454977b41336061f0a040f77dbf529f3a46d8095676ec971b940ab4c9642578760779840a3f9b3b893b2f65006c544e9c16586d33649769b7c1c94552d7efa081a56ad612dec932812676ebec091f2aed69123604f4888a125e04ff85f5a727c286664378581cf34c7ee13eb01cc4faf3308ed3c07a9415f98e5fbfe073e6c357967244e46ba6ebbe391d8154e6e4a1c80524b1a6733eca46e37bfdd62d75816988a79aac6bdb62a06b1237a8ff5e5c848d01bbff684248cf06d92f301623c893eb0fba0f3faee2d197ea57ac428f89d6c000f76d58d5aacc3d70204781aca45bc02b1456b454231d2f2ed4ca6614e5242c7d7af0fe61e9af6ecfa76674ffbc29b858091cbfb4011538f0e894ce45d21d7fac04ba2ff57e9ff6db21e2afd9468ad785c262ec59d4a1a801c5ec2f95fc107dc9cb5f7869d70aa84450b8c350c2fa48bddef20752a1e43676b246c7f59f8f1f4aee43c1a15f36f7a36a9ec708320ea42089991551f2656ec62ea38233946b85616ff182cf17cd227e596329b546ea04d13b053be4cf3338de777b50bc6eca7a6185cf7a5022bc9be3749b1bb43e10ecc88a0c580f2b7373138ee49c7bafd8be6a64048887230480b0c85a045255494e04a9a81646369ce7a10e08da6fae27333ec0c16c8a74d93779a9e055395078d0b07286f9930203010001";
  private static final String destinationAccountKeyHexProtoBytes = "0a220a20ec8cb9814ac5c1a4aacbc613e756744679050927cc9e5f8772c6d649d4a5ac0612220a20e7ef0b2772663314ecd7ee92008613764ab5669666d95bd2621d99d60506cb0d1a1e666f673a2f2f666f672e616c7068612e6d6f62696c65636f696e2e636f6d2aa60430820222300d06092a864886f70d01010105000382020f003082020a0282020100c853a8724bc211cf5370ed4dbec8947c5573bed0ec47ae14211454977b41336061f0a040f77dbf529f3a46d8095676ec971b940ab4c9642578760779840a3f9b3b893b2f65006c544e9c16586d33649769b7c1c94552d7efa081a56ad612dec932812676ebec091f2aed69123604f4888a125e04ff85f5a727c286664378581cf34c7ee13eb01cc4faf3308ed3c07a9415f98e5fbfe073e6c357967244e46ba6ebbe391d8154e6e4a1c80524b1a6733eca46e37bfdd62d75816988a79aac6bdb62a06b1237a8ff5e5c848d01bbff684248cf06d92f301623c893eb0fba0f3faee2d197ea57ac428f89d6c000f76d58d5aacc3d70204781aca45bc02b1456b454231d2f2ed4ca6614e5242c7d7af0fe61e9af6ecfa76674ffbc29b858091cbfb4011538f0e894ce45d21d7fac04ba2ff57e9ff6db21e2afd9468ad785c262ec59d4a1a801c5ec2f95fc107dc9cb5f7869d70aa84450b8c350c2fa48bddef20752a1e43676b246c7f59f8f1f4aee43c1a15f36f7a36a9ec708320ea42089991551f2656ec62ea38233946b85616ff182cf17cd227e596329b546ea04d13b053be4cf3338de777b50bc6eca7a6185cf7a5022bc9be3749b1bb43e10ecc88a0c580f2b7373138ee49c7bafd8be6a64048887230480b0c85a045255494e04a9a81646369ce7a10e08da6fae27333ec0c16c8a74d93779a9e055395078d0b07286f9930203010001";

  private static AccountKey senderAccountKey;
  private static AccountKey destinationAccountKey;

  @BeforeClass
  public static void setUp() {
    try {
      senderAccountKey = AccountKey.fromBytes(Hex.toByteArray(senderAccountKeyHexProtoBytes));
      destinationAccountKey = AccountKey
          .fromBytes(Hex.toByteArray(destinationAccountKeyHexProtoBytes));
    } catch (Exception e) {
      Log.e(TAG, "Exception during TxOutMemoParserTest setup: " + e.getMessage());
    }
  }


  @Test
  public void parseTxOutMemo_emptyDecryptedPayload_ReturnsNotSet() throws InvalidTxOutMemoException {
    byte[] emptyDecryptedMemoPayload = new byte[] {};
    TxOutMemo txOutMemo = TxOutMemoParser
        .parseTxOutMemo(emptyDecryptedMemoPayload, null, null);

        Assert.assertEquals(TxOutMemoType.NOT_SET, txOutMemo.getTxOutMemoType());
  }

  @Test
  public void parseTxOutMemo_senderMemoDecryptedPayload_ReturnsSenderMemo() throws Exception {
    String decryptedSenderMemoPayload = "0100ccb5a98f0c0c42f68491e5e0c936245200000000000000000000000093bc01819f06568225bc306641ccd307";
    TxOut txOut = TxOut.fromBytes(Hex.toByteArray("0a2d0a220a204a34f2846a357629e716590b37cde9d843f9cef1b4271eddc809e042f2bdeb32115497d726a58d17c812220a20e80556606d3631dc1ac4c25285f6aa21cbd311da514d961c1d71af2a5c660f621a220a20ba95abc24f7e609110cecde890cb0a7e6c59d660cda4b8bc8267f17aac69a13f22560a54c0d6bbfade4f1c504954d8c8b2be956a711bc2aa2754c2dee27ef0726b5c4371d1efb04f1248ee524fa46657277a10dd538e0919ede7b122fc65dbbe6681d7b15a2f03f14e190fde85fbb100eea6e394a86601002a300a2ea2a5150adfc75db67f3d915637a2857d015e21ef0b93ccf69e66d379a1bd852fc04efac800be7b2c5ec687f2a97c"));
    TxOutMemo txOutMemo = TxOutMemoParser
        .parseTxOutMemo(Hex.toByteArray(decryptedSenderMemoPayload), senderAccountKey, txOut);

    Assert.assertEquals(TxOutMemoType.SENDER, txOutMemo.getTxOutMemoType());
  }

  @Test
  public void parseTxOutMemo_destinationMemoDecryptedPayload_ReturnsDestinationMemo() throws Exception {
    String decryptedDestinationMemoPayload = "0200ab7b2b63966ea9a865df8d620f0b5fb001000000000000010000000000000002000000000000000000000000";
    TxOut txOut = TxOut.fromBytes(Hex.toByteArray("0a2d0a220a2092fb834c8fc5971037cdb4a69c4d41eec23f95fd590f4a203962a2f3655b2c4311a57c052c0220b51012220a202af885782c5ed80339d5c557f2a77afafaa0cd30cbc61fb50887106a0febd5491a220a20848dca7b19570e0674bee0159ebd073a7d521ebc6b9d6fd1e0e010fe9030f52522560a54efea78634d4030b6bce0ba05bdc3a8b2c56ae4d094b0571e2b48fbcdd2b786aced307e08090f59afa3ba78e128c92cad7b74e3ecc53d635bdf79a1c17fec3cc97824f76ffb94f0f1aba69e82c5136fb9a72c01002a300a2ee4b692f28157b4d3bdfe8883acdddcb483fd77c1706e92a0d88c196561666b6c629a106c602c115cb8bb14b3d301"));
    TxOutMemo txOutMemo = TxOutMemoParser
        .parseTxOutMemo(Hex.toByteArray(decryptedDestinationMemoPayload), destinationAccountKey, txOut);

    Assert.assertEquals(TxOutMemoType.DESTINATION, txOutMemo.getTxOutMemoType());
  }

  @Test
  public void parseTxOutMemo_unusedMemoDecryptedPayload_ReturnsUnusedMemo() throws Exception {
    String decryptedUnusedMemoPayload = "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
    TxOutMemo txOutMemo = TxOutMemoParser
        .parseTxOutMemo(Hex.toByteArray(decryptedUnusedMemoPayload), null, null);

    Assert.assertEquals(TxOutMemoType.UNUSED, txOutMemo.getTxOutMemoType());
  }
}