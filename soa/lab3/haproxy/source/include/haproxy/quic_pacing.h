#ifndef _HAPROXY_QUIC_PACING_H
#define _HAPROXY_QUIC_PACING_H

#include <haproxy/quic_pacing-t.h>

#include <haproxy/list.h>
#include <haproxy/quic_frame.h>

static inline void quic_pacing_init(struct quic_pacer *pacer,
                                    const struct quic_cc *cc)
{
	pacer->cc = cc;
	pacer->next = 0;
}

int quic_pacing_expired(const struct quic_pacer *pacer);

void quic_pacing_sent_done(struct quic_pacer *pacer, int sent);

enum quic_tx_err quic_pacing_send(struct quic_pacer *pacer, struct quic_conn *qc);

#endif /* _HAPROXY_QUIC_PACING_H */
