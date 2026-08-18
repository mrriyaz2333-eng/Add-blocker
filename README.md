# APP BLOCKER Suite — v1 starter

This is the first Android project foundation for the requested APP BLOCKER concept.

Included:
- Login/Owner entry screens
- Dark modern UI foundation
- Owner control area
- Timer presets
- Usage Access permission entry
- Overlay permission entry
- Local-only starter (no Firebase)
- Android 11+ compatible target architecture

Important:
The current starter does NOT silently control or bypass other apps. Real app blocking requires Android Usage Access/Accessibility or another appropriate user-granted mechanism and must be implemented transparently.

Next build stages:
1. Installed-app picker
2. Real blocking service using user-granted permissions
3. Persistent timer rules
4. Admin/Owner separated apps
5. Secure backend/API for Owner ↔ Admin ↔ User synchronization
6. Audit logs and permission enforcement
