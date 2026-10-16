# Security & publication notes

- The original archive contained a **Google Maps API key** in Android `local.properties`. This file was **excluded** from the public-ready copy. The previous key should be **revoked/rotated** in Google Cloud and any new key restricted to the Android app, certificate fingerprint and required Maps API.
- `android/local.properties.example` is a non-secret template. Create a local `android/local.properties` file when needed; `.gitignore` excludes it.
- Android resources may expose a Google Maps key at runtime by design. Do not treat client-side API keys as secret; restrict their use in Google Cloud.
- Both mobile applications contain a university API endpoint. Its availability, ownership and permission for continued use have not been verified. Avoid automated or public testing against that service without authorization.
- The original source has not undergone a comprehensive security audit, nor has functional execution been verified.
- Do not commit signing keys, user data, access tokens, build output or local machine paths.
