import JSEncrypt from "jsencrypt";

const publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQIrDkz7TpAolPqCmTIGnxgAdBIEXnmpbBA8JlvimpgXHf1AoJzICVvidx9HGTOJAdYFxF4Ip0JA+9vjflS/Lknk7+lxjdawIjIlrvSO13f3PC78OBP58zWuNhvI+zjtLUY0yXAySU2QZfRtuD4imF4l+oiOQwNFmN7/AUdKtF1wIDAQAB";

export function encodeRSA(word: string) {
	const jsRsa = new JSEncrypt();
	jsRsa.setPublicKey(publicKey);
	return jsRsa.encrypt(word);
}
