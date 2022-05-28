import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import { getSerialNumber, checkSDKActivation } from 'react-native-esper-sdk';

const accessToken = "yourAccessToken";

export default function App() {
  const [result, setResult] = React.useState<string | undefined>();

  React.useEffect(() => {
    const esperHandler = async () => {
      const isActive = await checkSDKActivation(accessToken);
      console.log("Esper SDK isActive:", isActive);
      if (isActive) {
        const serialNumber = await getSerialNumber();
        if (serialNumber != null) {
          console.log("Got serialNumber:", serialNumber);
          setResult(serialNumber);
        }
      }
    }

    esperHandler();
  }, []);

  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
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
