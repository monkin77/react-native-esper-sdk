import * as React from 'react';
import {useEffect, useState} from 'react';
import { StyleSheet, View, Text } from 'react-native';
import { getSerialNumber, getDeviceId, checkSDKActivation, getDeviceUUID } from 'react-native-esper-sdk';

const accessToken = "yourAccessToken";

export default function App() {
  const [esperId, setEsperId] = useState<string | undefined>();
  const [esperSerialNo, setEsperSerialNo] = useState<string | undefined>();
  const [esperUUID, setEsperUUID] = useState<string | undefined>();

  useEffect(() => {
    const esperHandler = async () => {
      const isActive = await checkSDKActivation(accessToken);
      console.log("Esper SDK isActive:", isActive);
      if (isActive) {
        const esperDeviceId = await getDeviceId();
        if (esperDeviceId != null) {
          console.log("Got esperDeviceId:", esperDeviceId);
          setEsperId(esperDeviceId);
        }

        const serialNumber = await getSerialNumber();
        if (serialNumber != null) {
          console.log("Got serialNumber:", serialNumber);
          setEsperSerialNo(serialNumber);
        }

        const uuid = await getDeviceUUID();
        if (uuid != null) {
          console.log("Got UUID:", uuid);
          setEsperUUID(uuid);
        }
      }
    }

    esperHandler();
  }, []);

  return (
    <View style={styles.container}>
      <Text>Esper Device ID: {esperId}</Text>
      <Text>Esper Serial No.: {esperSerialNo}</Text>
      <Text>Esper Device UUID: {esperUUID}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
