# react-native-esper-sdk

React Native Module that interacts with the Esper SDK

## Installation

```sh
npm install react-native-esper-sdk
```

### Android Manual Installation
1. In your android level build.gradle, add the following line to the repositories:
    - `maven { url "https://artifact.esper.io/artifactory/esper-device-sdk/" }`
2. Afterwards, add the following dependencies to the build.gradle inside `android/app` folder:
    ``` js
    // Provide the Esper SDK
    implementation 'io.esper.devicesdk:app:2.1.2851.18'
    implementation 'androidx.annotation:annotation:1.1.0'
    ``` 
3. Finnaly, **for android 11**, add the following code to AndroidManifest.xml:
    ``` js
    <queries>
            <package android:name="io.shoonya.shoonyadpc"/>
    </queries>
    ```

You can also check the [Esper SDK documentation](https://docs.esper.io/home/devicesdk.html#enabling-the-esper-sdk-in-your-application)
for a detailed explanation of the changes needed.

## Usage

```js
import { getSerialNumber, checkSDKActivation } from "react-native-esper-sdk";

// ...
const isActive = await checkSDKActivation(accessToken);
console.log("Esper SDK isActive:", isActive);
if (isActive) {
    const serialNumber = await getSerialNumber();
    if (serialNumber != null)
        console.log("Serial Number:", serialNumber);
}
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
