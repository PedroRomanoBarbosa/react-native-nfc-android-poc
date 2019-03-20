/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, { Component } from 'react';
import { Platform, StyleSheet, Text, View, TouchableOpacity, NativeModules } from 'react-native';
import NfcManager from 'react-native-nfc-manager';
import { Ndef, NfcTech, ByteParser, NfcAdapter } from 'react-native-nfc-manager/NfcManager';


const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android:
    'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

type Props = {};
export default class App extends Component<Props> {

  async componentWillMount() {
    // Check if NFC is supported
    try {
      const isSupported: boolean = await NfcManager.isSupported();
      console.log('NFC is supported: ', isSupported);
    } catch (error) {
      console.warn('Error occurred');
    }

    // Check if NFC is enabled
    try {
      const isEnabled: boolean = await NfcManager.isEnabled();
      console.log('NFC enabled: ', isEnabled);
    } catch (error) {
      console.warn('Error occurred');
    }

    // starting NFC
    try {
      const result = await NfcManager.start();
      console.log('start OK. Result:', result);
    } catch (error) {
      console.log('device does not support NFC');
    }

    // Directs user to go to nfc settings
    // await NfcManager.goToNfcSetting();

    // Register tag event
    await NfcManager.registerTagEvent(
      tag => {
        console.log('Tag Discovered', tag);
      },
      'Hold your device over the tag',
      {
        invalidateAfterFirstRead: true,
        isReaderModeEnabled: true,
        readerModeFlags: NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
      },
    );
    console.log('Tag listener registered');

    NativeModules.NFCLifecycleModule.nfcListenerReady();
    console.log('Notify listerner set');

    // Terminate NFC
    // NfcManager.stop();
    // console.log('NFC terminated');
  }

  render() {
    return (
      <View style={styles.container}>
        <TouchableOpacity onPress={async () => await NfcManager.goToNfcSetting()}>
          <Text style={styles.welcome}>Go to NFC settings!</Text>
        </TouchableOpacity>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
