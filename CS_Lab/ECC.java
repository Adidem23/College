package Cyber_Assignments;

import java.util.Random;

class G {
    private final int x, y, p, a;

    public G(int x, int y, int p, int a) {
        this.x = x;
        this.y = y;
        this.p = p;
        this.a = a;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public G add(G g) {
        assert this.a == g.a;
        if (this.y == 0)
            return new G(g.x, g.y, this.p, this.a);
        else if (g.y == 0)
            return new G(this.x, this.y, this.p, this.a);
        else if (this.x == g.x && this.y == (-g.y))
            return new G(this.x, 0, this.p, this.a);
        else if (this.x == g.x && this.y == g.y) {
            int t = (3 * this.x * this.x + this.a) * inv(2 * this.y, this.p);
            t = mod(t, this.p);
            int xr = mod(t * t - this.x - g.x, this.p);
            int yr = t * (this.x - xr) - this.y;
            yr = mod(yr, this.p);
            return new G(xr, yr, this.p, this.a);
        } else {
            int t = mod(g.y - this.y, this.p) * inv(mod(g.x - this.x, this.p),
                    this.p);
            t = mod(t, this.p);
            int xr = mod(t * t - this.x - g.x, this.p);
            int yr = t * (this.x - xr) - this.y;
            yr = mod(yr, this.p);
            return new G(xr, yr, this.p, this.a);
        }
    }

    public G subtract(G g) {
        return add(new G(g.x, -g.y, this.p, g.a));
    }

    public G multiply(int n) {
        G result = this;
        for (int i = 1; i < n; i++) {
            result = result.add(this);
        }
        return result;
    }

    private int inv(int num, int mod) {
        int[] result = new int[2];
        exgcd(num, mod, result);
        int x = result[0];
        int y = result[1];
        return ((x % mod) + mod) % mod;
    }

    private void exgcd(int a, int b, int[] result) {
        if (b == 0) {
            result[0] = 1;
            result[1] = 0;
            return;
        }
        exgcd(b, a % b, result);
        int temp = result[0];
        result[0] = result[1];
        result[1] = temp - (a / b) * result[1];
    }

    private int mod(int num, int mod) {
        return (num % mod + mod) % mod;
    }
}

class EllipticCurve {
    private int a, b, q;
    private G g;

    public EllipticCurve(int a, int b, int q, G g) {
        set(a, b, q, g);
    }

    public void set(int a, int b, int q, G g) {
        this.a = a;
        this.b = b;
        this.q = q;
        this.g = g;
    }

    public int getPrivateKey(int q) {
        return new Random().nextInt(q - 1) + 1;
    }

    public G getPublicKey(int privateKey, G g) {
        return g.multiply(privateKey);
    }

    public Pair<G, G> getEncryptedMessage(int senderPrivateKey, G
            receiverPublicKey, G message) {
        G c1 = g.multiply(senderPrivateKey);
        G c2 = message.add(receiverPublicKey.multiply(senderPrivateKey));
        return new Pair<>(c1, c2);
    }

    public G getDecryptedMessage(Pair<G, G> encryptedMessage, int
            receiverPrivateKey) {
        return
                encryptedMessage.second.subtract(encryptedMessage.first.multiply(receiverPrivateKey));
    }
}

public class ECC {
    public static void main(String[] args) {
        int q = 11, a = 1, b = 6;

        G g = new G(2, 7, q, a);

        EllipticCurve ec = new EllipticCurve(a, b, q, g);

        int nb = 7;

        G pb = ec.getPublicKey(nb, g);

        System.out.println("Public Key=" + pb.getX() + "," + pb.getY());

        int na = 3;

        G pm = new G(10, 9, q, a);

        Pair<G, G> cm = ec.getEncryptedMessage(na, pb, pm);

        System.out.println("Encrypt1Component=" + cm.first.getX() + "," +
                cm.first.getY());

        System.out.println("Encrypt2Component=" + cm.second.getX() + "," +
                cm.second.getY());

        G pm1 = ec.getDecryptedMessage(cm, nb);

        System.out.println("Original Message=" + pm1.getX() + "," +
                pm1.getY());
    }
}

class Pair<X, Y> {
    public final X first;
    public final Y second;

    public Pair(X first, Y second) {
        this.first = first;
        this.second = second;
    }
}
