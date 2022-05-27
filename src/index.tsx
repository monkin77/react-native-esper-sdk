import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-esper-sdk' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const EsperSdkManager = NativeModules.EsperSdkManager
  ? NativeModules.EsperSdkManager
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function checkSDKActivation(accessToken : string) : boolean {
  return EsperSdkManager.checkSDKActivation(accessToken);
}

export function getDeviceId(callback: (error : boolean, deviceId : string) => void ) : void {
  return EsperSdkManager.getDeviceId(callback);
}

export function getSerialNumber(callback: (error : boolean, deviceId : string) => void ) : void {
  return EsperSdkManager.getSerialNumber(callback);
}
